package com.aziza.santridear.models;

public class Kerbersihan {
    String Santri;
    String Kelas;
    Boolean isPresent;

    public Kerbersihan() {
    }

    public Kerbersihan(String santri, String kelas, Boolean isPresent) {
        Santri = santri;
        Kelas = kelas;
        isPresent = isPresent;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
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
