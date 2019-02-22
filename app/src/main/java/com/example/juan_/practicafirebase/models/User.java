package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class User {

    private String email;
    private String nombre;
    private String apellidos;
    private int telefono;
    // private ListGroups groups;

    public User(){

    }

    public User(User a){
        this.email = a.getEmail();
        this.nombre = a.nombre;
        this.apellidos = a.apellidos;
        this.telefono = a.telefono;
    }

    public User(String email, String nombre, String apellidos, int telefono) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        //this.groups = ls;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }


/*    public ListGroups getGroups() {
        return groups;
    }

    public void setGroups(ListGroups groups) {
        this.groups = groups;
    }*/
}
