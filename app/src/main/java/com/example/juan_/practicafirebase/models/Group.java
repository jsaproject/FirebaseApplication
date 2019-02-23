package com.example.juan_.practicafirebase.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class Group implements Serializable {


    private String nombreGrupo;
    private UserList usuarios;
    private MessageList listaMensajes;

    public Group(){

    }

    public Group(String nombreGrupo, UserList u) {
        this.nombreGrupo = nombreGrupo;
        this.usuarios = u;
        this.listaMensajes = new MessageList();
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public UserList getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UserList usuarios) {
        this.usuarios = usuarios;
    }

    public MessageList getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(MessageList listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
}
