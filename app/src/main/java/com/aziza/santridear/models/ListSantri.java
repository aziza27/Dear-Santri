package com.aziza.santridear.models;

import com.google.firebase.firestore.PropertyName;

public class ListSantri {
    String Santri;
    String Kelas;

    public ListSantri(){}

    public ListSantri(String santri,String kelas) {
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
