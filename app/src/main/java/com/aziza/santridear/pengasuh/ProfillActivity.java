package com.aziza.santridear.pengasuh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aziza.santridear.R;
import com.aziza.santridear.santri.SantriActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfillActivity extends AppCompatActivity{
    private static final int GALLERY_INTENT_CODE=1023;
        TextView nama_profil,nama,nama_lengkap,kelas;
        FrameLayout beranda;
        String userId;
        DatabaseReference dr;
        FirebaseAuth auth;
        FirebaseFirestore ft;
        FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profill);

        nama = findViewById(R.id.nama);
        nama_profil = findViewById(R.id.nama_profil);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        kelas = findViewById(R.id.kelas);
        beranda = findViewById(R.id.beranda);

        auth = FirebaseAuth.getInstance();
        ft = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();


        DocumentReference dr = ft.collection("Pengasuh").document(userId);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                nama.setText(documentSnapshot.getString("pengasuh"));
                nama_profil.setText(documentSnapshot.getString("pengasuh"));
                nama_lengkap.setText(documentSnapshot.getString("pengasuh_lengkap"));
            }
        });

        beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(ProfillActivity.this, AdmiinActivity.class);
                startActivity(s);
                finish();
            }
        });

    }




}



