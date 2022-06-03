package model;

import java.util.Date;

public class Postagem {
    private int id;
    private String autor;
    private String titulo;
    private String texto;
    private int usuariofk;
    private Comentarios[] comentarios;
    private Date data;

    public Postagem() {
        this.autor = "";
        this.titulo = "";
    }

    public Postagem(int id, String autor, String titulo, Comentarios[] comentarios) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.comentarios = comentarios;
    }

    public Postagem(String titulo, String texto, int usuariofk) {
        this.titulo = titulo;
        this.texto = texto;
        this.usuariofk = usuariofk;
    }

    public int getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public Comentarios[] getComentarios() {
        return comentarios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setComentarios(Comentarios[] comentarios) {
        this.comentarios = comentarios;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getUsuariofk() {
        return usuariofk;
    }

    public void setUsuariofk(int usuariofk) {
        this.usuariofk = usuariofk;
    }
}
