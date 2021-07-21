package com.aziza.santridear.santri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aziza.santridear.R;
import com.aziza.santridear.Tentang;
import com.aziza.santridear.intro.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SantriActivity extends AppCompatActivity {
    CardView cardLogout,cardProfil,cardNotif,cardTentang;
    TextView namaSantri,kelasSantri;
    String userId;
    FirebaseAuth auth;
    FirebaseFirestore ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santri);

        auth = FirebaseAuth.getInstance();
        ft = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();


        DocumentReference dr = ft.collection("santri").document(userId);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namaSantri.setText(documentSnapshot.getString("santri_lengkap"));
                kelasSantri.setText(documentSnapshot.getString("kelas"));
            }
        });

        init();
        cardFunction();


    }

    private void cardFunction() {
        cardLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SantriActivity.this, LoginActivity.class));
        });

        cardTentang.setOnClickListener(view -> {

            Intent tentang = new Intent(SantriActivity.this, Tentang.class);
            startActivity(tentang);
            finish();
        });

        cardProfil.setOnClickListener(view -> {
            Intent profil = new Intent(SantriActivity.this,ProfilSantri.class);
            startActivity(profil);
            finish();
        });

        cardNotif.setOnClickListener(view -> {
            Intent notif = new Intent(SantriActivity.this,NotifikasiActivity.class);
            startActivity(notif);
            finish();
        });


    }

    private void init(){
        cardLogout = findViewById(R.id.crdLogout);
        cardNotif = findViewById(R.id.crdNotif);
        cardProfil = findViewById(R.id.crdProfil);
        cardTentang = findViewById(R.id.crdTentang);
        namaSantri=findViewById(R.id.namaSantri);
        kelasSantri=findViewById(R.id.kelasSantri);


    }
}
