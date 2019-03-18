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
    private int num_last_photo;

    public Group(){

    }

    public Group(String nombreGrupo, UserList usuarios, MessageList listaMensajes, int num_last_photo) {
        this.nombreGrupo = nombreGrupo;
        this.usuarios = usuarios;
        this.listaMensajes = listaMensajes;
        this.num_last_photo = num_last_photo;
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

    public int getNum_last_photo() {
        return num_last_photo;
    }

    public void setNum_last_photo(int num_last_photo) {
        this.num_last_photo = num_last_photo;
    }
}
