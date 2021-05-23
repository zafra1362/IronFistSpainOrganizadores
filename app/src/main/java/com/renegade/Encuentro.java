package com.renegade;

import java.time.LocalDateTime;
import java.util.List;

public class Encuentro {
    String enlace;
    String estado;
    LocalDateTime fechaPeticion;
    LocalDateTime fechaEncuentro;
    String uidLocal;
    String uidVisitante;
    String organizador;
    String resultadoLocal;
    String resultadoVisitante;
    List<Integer> diasSeleccionados;
    String rangoHoraMin;
    String rangoHoraMax;
    String id;
    Long firstTo;

    public Encuentro(String estado, String uidLocal, String uidVisitante, List<Integer> diasSeleccionados, String rangoHoraMin, String rangoHoraMax, String id) {
        this.estado = estado;
        this.uidLocal = uidLocal;
        this.uidVisitante = uidVisitante;
        this.diasSeleccionados = diasSeleccionados;
        this.rangoHoraMin = rangoHoraMin;
        this.rangoHoraMax = rangoHoraMax;
        this.id = id;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPeticion() {
        return fechaPeticion;
    }

    public void setFechaPeticion(LocalDateTime fechaPeticion) {
        this.fechaPeticion = fechaPeticion;
    }

    public LocalDateTime getFechaEncuentro() {
        return fechaEncuentro;
    }

    public void setFechaEncuentro(LocalDateTime fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    public String getUidLocal() {
        return uidLocal;
    }

    public void setUidLocal(String uidLocal) {
        this.uidLocal = uidLocal;
    }

    public String getUidVisitante() {
        return uidVisitante;
    }

    public void setUidVisitante(String uidVisitante) {
        this.uidVisitante = uidVisitante;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getResultadoLocal() {
        return resultadoLocal;
    }

    public void setResultadoLocal(String resultadoLocal) {
        this.resultadoLocal = resultadoLocal;
    }

    public String getResultadoVisitante() {
        return resultadoVisitante;
    }

    public void setResultadoVisitante(String resultadoVisitante) {
        this.resultadoVisitante = resultadoVisitante;
    }

    public List<Integer> getDiasSeleccionados() {
        return diasSeleccionados;
    }

    public void setDiasSeleccionados(List<Integer> diasSeleccionados) {
        this.diasSeleccionados = diasSeleccionados;
    }

    public String getRangoHoraMin() {
        return rangoHoraMin;
    }

    public void setRangoHoraMin(String rangoHoraMin) {
        this.rangoHoraMin = rangoHoraMin;
    }

    public String getRangoHoraMax() {
        return rangoHoraMax;
    }

    public void setRangoHoraMax(String rangoHoraMax) {
        this.rangoHoraMax = rangoHoraMax;
    }

    public Long getFirstTo() {
        return firstTo;
    }

    public void setFirstTo(Long firstTo) {
        this.firstTo = firstTo;
    }
}
