package dao;

import java.sql.*;

import org.eclipse.jetty.client.api.Request;
import org.json.simple.JSONObject;

import model.Usuario;
import model.Postagem;
import model.Comentarios;

public class DAO {
	public Connection conexao;

	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirUsuario(Usuario usuario) throws Exception {
		boolean status = false;
		try {

			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("INSERT INTO users (nome, email, senha)"
					+ "VALUES ('" + usuario.getNome() + "', '" + usuario.getEmail() + "', '"
					+ usuario.getSenha() + "') RETURNING id");
			if (rs.next()) {
				usuario.setId(rs.getInt("id"));
			}
			st.executeUpdate("INSERT INTO endereco (estado, cidade, rua,numero,complemento,usuariofk)"
					+ "VALUES ('" + usuario.getEstado() + "', '" + usuario.getCidade() + "', '"
					+ usuario.getRua() + "'," + usuario.getNumero() + ",'" + usuario.getComplemento() + "',"
					+ usuario.getId() + ")");
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("tres");
			throw new RuntimeException(u);
		}
		return status;
	}

	public Usuario getUsuario(String email) {
		Usuario usuario = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st
					.executeQuery("SELECT * FROM users JOIN endereco ON endereco.usuariofk=users.id WHERE users.email='"
							+ email + "'");
			if (rs.next()) {
				rs.last();
				rs.beforeFirst();
				for (int i = 0; rs.next(); i++) {
					usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"),
							rs.getString("senha"),
							rs.getString("estado"), rs.getString("cidade"), rs.getString("rua"), rs.getInt("numero"),
							rs.getString("complemento"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public Postagem[] getPostagens() {
		Postagem[] Postagens = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * from postagens JOIN users on postagens.usuariofk=users.id");
			if (rs.next()) {
				rs.last();
				Postagens = new Postagem[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					Postagens[i] = new Postagem();
					Postagens[i].setId(rs.getInt("id"));
					Postagens[i].setAutor(rs.getString("nome"));
					Postagens[i].setTitulo(rs.getString("titulo"));
					Postagens[i].setTexto(rs.getString("texto"));
					Postagens[i].setData(rs.getDate("data"));
				}
			}
		} catch (Exception e) {
		}
		return Postagens;

	}

	public Postagem getPostagem(int id) {
		Postagem Postagem = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = st.executeQuery(
					"SELECT postagens.id,postagens.titulo,postagens.texto,postagens.data,users.nome FROM postagens JOIN users on postagens.usuariofk=users.id WHERE postagens.id ="
							+ id);

			if (rs.next()) {
				Postagem = new Postagem();
				Postagem.setId(rs.getInt("id"));
				Postagem.setAutor(rs.getString("nome"));
				Postagem.setTitulo(rs.getString("titulo"));
				Postagem.setTexto(rs.getString("texto"));
				Postagem.setData(rs.getDate("data"));
				ResultSet rs2 = st.executeQuery(
						"SELECT comentarios.id,comentarios.texto,comentarios.data,users.nome FROM comentarios JOIN users ON comentarios.usuariofk=users.id WHERE postagemfk ="
								+ id);
				if (rs2.next()) {
					rs2.last();
					Comentarios[] Comentarios = new Comentarios[rs2.getRow()];
					rs2.beforeFirst();
					for (int i = 0; rs2.next(); i++) {
						Comentarios[i] = new Comentarios();
						Comentarios[i].setId(rs2.getInt("id"));
						Comentarios[i].setTexto(rs2.getString("texto"));
						Comentarios[i].setUsuario(rs2.getString("nome"));
						Comentarios[i].setData(rs2.getDate("data"));
					}
					Postagem.setComentarios(Comentarios);
				}
			}
			st.close();
		} catch (Exception e) {
			System.out.println("Error dao" + e);
		}
		return Postagem;
	}

	public boolean criarPost(Postagem postagem, JSONObject res) {
		boolean status = false;
		int id;
		try {

			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("INSERT INTO postagens (titulo, texto, usuariofk)"
					+ "VALUES ('" + postagem.getTitulo() + "', '" + postagem.getTexto() + "', "
					+ postagem.getUsuariofk() + ") RETURNING id");
			if (rs.next()) {
				id = rs.getInt("id");
				res.put("id", id);
				status = true;
			}
		} catch (Exception e) {
			res.put("Erro", "Erro ao criar post");
			System.out.println("Error dao" + e);
		}

		return status;
	}

	public boolean criarComentario(Comentarios comentario, JSONObject res) {
		boolean status = false;
		int id;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("INSERT INTO comentarios (texto, usuariofk, postagemfk)"
					+ "VALUES ('" + comentario.getTexto() + "', " + comentario.getUsuariofk() + ", "
					+ comentario.getPostagemfk() + ") RETURNING id");
			if (rs.next()) {
				id = rs.getInt("id");
				res.put("id", id);
				status = true;
			}
		} catch (Exception e) {
			res.put("Erro", "Erro ao postar comentario");
			System.out.println("Error dao" + e);
		}

		return status;
	}
	// public boolean atualizarUsuario(Usuario usuario) {
	// boolean status = false;
	// try {
	// Statement st = conexao.createStatement();
	// String sql = "UPDATE usuario SET login = '" + usuario.getLogin() + "', senha
	// = '"
	// + usuario.getSenha() + "', sexo = '" + usuario.getSexo() + "'"
	// + " WHERE codigo = " + usuario.getCodigo();
	// st.executeUpdate(sql);
	// st.close();
	// status = true;
	// } catch (SQLException u) {
	// throw new RuntimeException(u);
	// }
	// return status;
	// }

	// public boolean excluirUsuario(int codigo) {
	// boolean status = false;
	// try {
	// Statement st = conexao.createStatement();
	// st.executeUpdate("DELETE FROM usuario WHERE codigo = " + codigo);
	// st.close();
	// status = true;
	// } catch (SQLException u) {
	// throw new RuntimeException(u);
	// }
	// return status;
	// }

	// public Usuario[] getUsuariosMasculinos() {
	// Usuario[] usuarios = null;

	// try {
	// Statement st =
	// conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	// ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.sexo LIKE
	// 'M'");
	// if(rs.next()){
	// rs.last();
	// usuarios = new Usuario[rs.getRow()];
	// rs.beforeFirst();

	// for(int i = 0; rs.next(); i++) {
	// usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"),
	// rs.getString("senha"), rs.getString("sexo").charAt(0));
	// }
	// }
	// st.close();
	// } catch (Exception e) {
	// System.err.println(e.getMessage());
	// }
	// return usuarios;
	// }
}