package com.aziza.santridear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aziza.santridear.intro.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SantriActivity extends AppCompatActivity {


    CardView cardLogout,cardProfil,cardNotif,cardTentang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santri);
        init();
        cardFunction();


    }

    private void cardFunction() {
        cardLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SantriActivity.this, LoginActivity.class));
        });

        cardTentang.setOnClickListener(view -> {

            Intent tentang = new Intent(SantriActivity.this,Tentang.class);
            startActivity(tentang);
            finish();
        });

        cardProfil.setOnClickListener(view -> {
            Intent profil = new Intent(SantriActivity.this,ProfilSantri.class);
            startActivity(profil);
            finish();
        });

        cardNotif.setOnClickListener(view -> {
            Intent notif = new Intent(SantriActivity.this,ProfilSantri.class);
            startActivity(notif);
            finish();
        });


    }

    private void init(){
        cardLogout = findViewById(R.id.crdLogout);
        cardNotif = findViewById(R.id.crdNotif);
        cardProfil = findViewById(R.id.crdProfil);
        cardTentang = findViewById(R.id.crdTentang);

    }
}
