package com.example.juan_.practicafirebase.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class User implements Serializable {

    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;

    public User(){

    }

    public User(User a){
        this.email = a.getEmail();
        this.nombre = a.nombre;
        this.apellidos = a.apellidos;
        this.telefono = a.telefono;
    }

    public User(String email, String nombre, String apellidos, String telefono) {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
