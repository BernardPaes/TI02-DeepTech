package app;

import static spark.Spark.*;
import service.UsuarioService;
import service.PostagemService;
import dao.DAO;

public class Aplicacao {

	private static UsuarioService usuarioService = new UsuarioService();
	private static PostagemService postagemService = new PostagemService();

	public static void main(String[] args) {

		port(5678);
		DAO dao = new DAO();
		dao.conectar();
		post("/login", (request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return usuarioService.login(request, response, dao);
		});

		post("/cadastro",(request,response)->{
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return usuarioService.cadastro(request, response, dao);
		});

		get("/postagens",(request,response)->{
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return postagemService.getPostagens(request, response, dao);
		});

		get("/postagem/:id",(request,response)->{
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return postagemService.getPostagem(request, response, dao);
		});

		post("/novapostagem",(request,response)->{
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return postagemService.newPost(request, response, dao);
		});
		
		post("/postagem/:id",(request,response)->{
			response.header("Access-Control-Allow-Origin", "*");
			response.type("text/json");
			return postagemService.newComentario(request, response, dao);
		});



		// get("/teste", (request, response) -> {return usuarioService.get(request,
		// response);});

	}
}