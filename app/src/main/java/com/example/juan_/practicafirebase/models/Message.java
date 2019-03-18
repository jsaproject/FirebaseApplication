package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String mensaje;
    private String autor;
    private String fecha;
    private boolean foto;

    public Message() {

    }

    public Message(String mensaje, String autor, String fecha, boolean foto) {
        this.mensaje = mensaje;
        this.autor = autor;
        this.fecha = fecha;
        this.foto = foto;
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

    public boolean isFoto() {
        return foto;
    }

    public void setFoto(boolean foto) {
        this.foto = foto;
    }


}

