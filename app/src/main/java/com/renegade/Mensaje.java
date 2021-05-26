package com.renegade;

public class Mensaje {
    public String autorEmail;
    public String autorNombre;
    public String autorFoto;
    public String mensaje;
    public String fecha;

    public Mensaje(String autorEmail, String autorNombre, String mensaje, String fecha) {
        this.autorEmail = autorEmail;
        this.autorNombre = autorNombre;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getAutorEmail() {
        return autorEmail;
    }

    public void setAutorEmail(String autorEmail) {
        this.autorEmail = autorEmail;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public String getAutorFoto() {
        return autorFoto;
    }

    public void setAutorFoto(String autorFoto) {
        this.autorFoto = autorFoto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
