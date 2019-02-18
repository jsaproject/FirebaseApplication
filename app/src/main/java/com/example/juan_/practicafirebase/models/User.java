package com.example.juan_.practicafirebase.models;


import com.google.firebase.database.IgnoreExtraProperties;


public class User {

    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private int telefono;

    public User(){

    }

    public User(String email, String password, String nombre, String apellidos, int telefono) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
