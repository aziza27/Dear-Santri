package com.aziza.santridear.models;

public class Salat {
    String Santri;
    String Kelas;
    String isPresent;

    public Salat() {
    }

    public Salat(String santri, String kelas, String isPresent) {
        Santri = santri;
        Kelas = kelas;
        isPresent = isPresent;

    }

    public String getPresent() {
        return isPresent;
    }

    public void setPresent(String present) {
        isPresent = present;
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
}
