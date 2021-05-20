package com.renegade;

public class Organizador {
    String nombre;
    String enlace;
    String rol;

    public Organizador(String nombre, String enlace) {
        this.nombre = nombre;
        this.enlace = enlace;
        rol = "organizador";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
