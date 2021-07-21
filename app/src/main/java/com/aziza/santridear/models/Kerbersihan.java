package com.aziza.santridear.models;

public class Kerbersihan {
    String Santri;
    String Kelas;

    public Kerbersihan() {
    }

    public Kerbersihan(String santri, String kelas) {
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
