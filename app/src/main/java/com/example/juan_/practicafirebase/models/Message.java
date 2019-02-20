package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String mensaje;
    private User autor;

    public Message(){

    }

    public Message(String mensaje, User autor) {
        this.mensaje = mensaje;
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }
}
