package model;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private String senha;
	private String estado;
	private String cidade;
	private String rua;
	private int numero;
	private String complemento;

	public Usuario() {
		this.nome = "";
		this.email = "";
		this.senha = "";
	}

	public Usuario(int id, String nome, String email, String senha, String estado, String cidade, String rua,
			int numero,
			String complemento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.estado = estado;
		this.cidade = cidade;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}


	public Usuario( String nome, String email, String senha, String estado, String cidade, String rua,
			int numero,
			String complemento) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.estado = estado;
		this.cidade = cidade;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}

	
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public String getRua() {
		return rua;
	}

	public int getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
