package service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import java.time.LocalDate;
import java.time.LocalDateTime;

import spark.Request;
import spark.Response;
import org.apache.commons.codec.digest.*;
import dao.DAO;
import model.Usuario;

public class UsuarioService {

	public JSONObject login(Request request, Response response, DAO dao) {
		String email = "";
		String senha = "";
		JSONObject res = new JSONObject();
		response.status(401);
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(request.body());
			email = (String) obj.get("email");
			senha = (String) obj.get("senha");
			senha = DigestUtils.sha1Hex("ti2cc" + senha);
		} catch (Exception e) {
			response.status(500);
			res.put("Erro", "Erro interno");
			return res;
		}
		Usuario usuario = dao.getUsuario(email);
		if (senha.equals(usuario.getSenha())) {
			JSONObject endereco = new JSONObject();
			response.status(200);
			res.put("Nome", usuario.getNome());
			res.put("Email", usuario.getEmail());
			endereco.put("Estado", usuario.getEstado());
			endereco.put("Cidade", usuario.getCidade());
			endereco.put("Rua", usuario.getRua());
			endereco.put("Numero", usuario.getNumero());
			if (usuario.getComplemento().length() > 0) {
				endereco.put("Complemento", usuario.getComplemento());
			}
			res.put("Endereco", endereco);

		} else {
			response.status(401);
			res.put("Error", "Usuario ou senha invalida");
		}
		return res;

	}

	public JSONObject cadastro(Request request, Response response, DAO dao) {
		String nome = "";
		String email = "";
		String senha = "";
		String estado = "";
		String cidade = "";
		String rua = "";
		int numero;
		String complemento = "";
System.out.println("um");
		JSONObject res = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(request.body());
			nome = (String) obj.get("nome");
			email = (String) obj.get("email");
			senha = (String) obj.get("senha");
			senha = DigestUtils.sha1Hex("ti2cc" + senha);
			estado = (String) obj.get("estado");
			cidade = (String) obj.get("cidade");
			rua = (String) obj.get("rua");
			numero = Integer.parseInt(String.valueOf(obj.get("numero")));
			if (obj.containsKey("complemento")) {
				complemento = (String) obj.get("complemento");
			}
		} catch (Exception e) {
			response.status(500);
			res.put("Erro", e);
			return res;
		}
		Usuario usuario = new Usuario(nome, email, senha, estado, cidade, rua, numero, complemento);
		try {

			if (dao.inserirUsuario(usuario)) {
				response.status(200);
				res.put("Message", "Usuario cadastrado com sucesso");
			}
		} catch (Exception e) {
			System.out.println(e);
			response.status(200);
			res.put("Error", "Usuario ja cadastrado");
			res.put("ErrorMessage", e);

		}
		return res;

	}

}