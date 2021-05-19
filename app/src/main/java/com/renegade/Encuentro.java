package com.renegade;

import java.util.List;

public class Encuentro {
    String rangoHoraMin;
    String rangoHoraMax;
    String nicknameRival1;
    List<String> diasDisponibles;
    String uidRival1;
    String uidRival2;
    String id;
    String nicknameRival2;

    public Encuentro(String nicknameRival1, String nicknameRival2, String rangoHoraMin, String rangoHoraMax, List<String> diasDisponibles, String uidRival1, String uidRival2, String id) {
        this.nicknameRival1 = nicknameRival1;
        this.nicknameRival2 = nicknameRival2;
        this.rangoHoraMin = rangoHoraMin;
        this.rangoHoraMax = rangoHoraMax;
        this.diasDisponibles = diasDisponibles;
        this.uidRival1 = uidRival1;
        this.uidRival2 = uidRival2;
        this.id = id;
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

    public String getNicknameRival1() {
        return nicknameRival1;
    }

    public void setNicknameRival1(String nicknameRival1) {
        this.nicknameRival1 = nicknameRival1;
    }

    public List<String> getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(List<String> diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }

    public String getUidRival1() {
        return uidRival1;
    }

    public void setUidRival1(String uidRival1) {
        this.uidRival1 = uidRival1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNicknameRival2() {
        return nicknameRival2;
    }

    public void setNicknameRival2(String nicknameRival2) {
        this.nicknameRival2 = nicknameRival2;
    }

    public String getUidRival2() {
        return uidRival2;
    }

    public void setUidRival2(String uidRival2) {
        this.uidRival2 = uidRival2;
    }
}
