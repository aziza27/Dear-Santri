package com.aziza.santridear.models;

public class Salat {
    String Santri;
    String Kelas;
    Boolean isPresent;

    public Salat() {
    }

    public Salat(String santri, String kelas,Boolean isPresent) {
        Santri = santri;
        Kelas = kelas;
        isPresent = isPresent;
    }

    public String getSantri() {
        return Santri;
    }

    public void setSantri(String santri) {
        Santri = santri;
    }

    public String getKelas() {
        return Kelas;
    }

    public void setKelas(String kelas) {
        Kelas = kelas;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }
}
