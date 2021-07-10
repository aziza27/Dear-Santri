package com.aziza.santridear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aziza.santridear.intro.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SantriActivity extends AppCompatActivity {


    CardView cardLogout, cardKegiatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santri);
        init();
        cardFunction();








    }

    private void cardFunction() {
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SantriActivity.this, LoginActivity.class));
            }
        });
        cardKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SantriActivity.this, DatasantriActivity.class));
            }
        });
    }

    private void init(){
        cardLogout = findViewById(R.id.crdLogout);
        cardKegiatan = findViewById(R.id.crdKegiatan);

    }
}
