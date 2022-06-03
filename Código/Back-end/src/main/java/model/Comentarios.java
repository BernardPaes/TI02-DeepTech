package model;

import java.util.Date;

public class Comentarios {
    private int id;
    private String texto;
    private int postagemfk;
    private int usuariofk;
    private String usuario;
    private Date data;

    public Comentarios() {

    }

    public Comentarios(int id, String texto, int usuariofk) {
        this.id = id;
        this.texto = texto;
        this.usuariofk = usuariofk;
    }

    public int getId() {
        return id;
    }

    public int getPostagemfk() {
        return postagemfk;
    }

    public String getTexto() {
        return texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getUsuariofk() {
        return usuariofk;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostagemfk(int postagemfk) {
        this.postagemfk = postagemfk;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setUsuariofk(int usuariofk) {
        this.usuariofk = usuariofk;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
