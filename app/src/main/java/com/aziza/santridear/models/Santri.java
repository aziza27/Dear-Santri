package com.aziza.santridear.models;

public class Santri {
    private String username,kelas,santri,santri_lengkap,Password;


    public Santri() {

    }

    public Santri(String username, String kelas, String santri,String santri_lengkap, String password) {
        this.username = username;
        this.kelas = kelas;
        this.santri = santri;
        this.santri_lengkap = santri_lengkap;
        this.Password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String walisantri) {
        this.kelas = kelas;
    }

    public String getSantri() {
        return santri;
    }
    public String getSantri_lengkap() {
        return santri_lengkap;
    }

    public void setSantri(String santri) {
        this.santri = santri;
    }
    public void setSantri_lengkap(String santri_lengkap) {
        this.santri_lengkap = santri_lengkap;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
