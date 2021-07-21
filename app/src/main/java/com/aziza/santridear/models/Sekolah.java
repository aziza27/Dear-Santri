package com.aziza.santridear.models;

public class Sekolah {
    String Santri;
    String Kelas;
    Boolean isPresent;
    public Sekolah() {
    }

    public Sekolah(String santri, String kelas, Boolean isPresent) {
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

