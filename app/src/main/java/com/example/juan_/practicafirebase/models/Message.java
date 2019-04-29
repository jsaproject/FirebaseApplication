package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String mensaje;
    private String autor;
    private String fecha;
    private String foto;
    private String fotoPerfil;

    public Message() {

    }

    public Message(String mensaje, String autor, String fecha, String foto, String fotoPerfil) {
        this.mensaje = mensaje;
        this.autor = autor;
        this.fecha = fecha;
        this.foto = foto;
        this.fotoPerfil = fotoPerfil;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}

