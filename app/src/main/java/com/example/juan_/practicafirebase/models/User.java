package com.example.juan_.practicafirebase.models;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class User implements Serializable {

    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String username;
    private String status;
    private String uriphoto;

    public User(){

    }

    public User(User a){
        this.email = a.getEmail();
        this.nombre = a.getNombre();
        this.apellidos = a.getApellidos();
        this.telefono = a.getTelefono();
        this.username = a.getUsername();
        this.status = a.getStatus();
        this.uriphoto= a.getUriphoto();
    }



    public User(String email, String nombre, String apellidos, String telefono, String username, String status, String uriphoto) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.username = username;
        this.status = status;
        this.uriphoto = uriphoto;
    }

    public String getUriphoto() {
        return uriphoto;
    }

    public void setUriphoto(String uriphoto) {
        this.uriphoto = uriphoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
