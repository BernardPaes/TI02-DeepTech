package service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.Request;
import spark.Response;
import dao.DAO;
import model.Comentarios;
import model.Postagem;

public class PostagemService {

    public JSONArray getPostagens(Request request, Response response, DAO dao) {
        JSONArray res = new JSONArray();
        Postagem[] postagens = dao.getPostagens();
        for (int i = 0; i < postagens.length; i++) {
            JSONObject postagem = new JSONObject();
            postagem.put("Titulo", postagens[i].getTitulo());
            postagem.put("Autor", postagens[i].getAutor());
            postagem.put("Texto", postagens[i].getTexto());
            postagem.put("Data", postagens[i].getData().toString());
            res.add(postagem);
        }
        return res;
    }

    public JSONObject getPostagem(Request request, Response response, DAO dao) {
        JSONObject res = new JSONObject();
        JSONArray comentarios = new JSONArray();
        Postagem postagem = null;
        try {
            postagem = dao.getPostagem(Integer.parseInt(request.params(":id")));

        } catch (Exception e) {

        }
        if (postagem == null) {
            res.put("Erro", "Nao existe esta postagem");
            return res;
        }
        res.put("Titulo", postagem.getTitulo());
        res.put("Texto", postagem.getTitulo());
        res.put("Autor", postagem.getTitulo());
        res.put("Data", postagem.getTitulo());
        for (int i = 0; i < postagem.getComentarios().length; i++) {
            JSONObject comentario = new JSONObject();
            comentario.put("Texto", postagem.getComentarios()[i].getTexto());
            comentario.put("Usuario", postagem.getComentarios()[i].getUsuario());
            comentario.put("Data", postagem.getComentarios()[i].getData());
            comentarios.add(comentario);
        }
        res.put("Comentarios", comentarios);
        return res;
    }

    public JSONObject newPost(Request request, Response response, DAO dao) {
        JSONObject res = new JSONObject();
        String titulo = "";
        String texto = "";
        int usuariofk;
        Postagem postagem = new Postagem();
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(request.body());
            titulo = (String) obj.get("titulo");
            texto = (String) obj.get("texto");
            usuariofk = Integer.parseInt(String.valueOf(obj.get("idUsuario")));
            postagem.setTitulo(titulo);
            postagem.setTexto(texto);
            postagem.setUsuariofk(usuariofk);

            if (dao.criarPost(postagem, res)) {
                response.status(200);
                res.put("Message", "Post criado com sucesso");
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        return res;
    }

    public JSONObject newComentario(Request request, Response response, DAO dao) {
        JSONObject res = new JSONObject();
        String texto = "";
        int postagemfk;
        int usuariofk;
        Comentarios comentario = new Comentarios();
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(request.body());
            texto = (String) obj.get("texto");
            usuariofk = Integer.parseInt(String.valueOf(obj.get("idUsuario")));
            postagemfk = Integer.parseInt(request.params(":id"));
            comentario.setTexto(texto);
            comentario.setUsuariofk(usuariofk);
            comentario.setPostagemfk(postagemfk);
            if (dao.criarComentario(comentario, res)) {
                response.status(200);
                res.put("Message", "Comentario criado com sucesso");
            }
        } catch (Exception e) {
            res.put("Erro", "Dados invalidos");
            System.out.println("erro antes" + e);
        }
        return res;
    }

}
