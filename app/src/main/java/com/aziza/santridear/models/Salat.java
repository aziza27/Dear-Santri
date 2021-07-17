package com.aziza.santridear.models;

public class Salat {
    String Santri;
    String Kelas;

    public Salat() {
    }

    public Salat(String santri, String kelas) {
        Santri = santri;
        Kelas = kelas;
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
