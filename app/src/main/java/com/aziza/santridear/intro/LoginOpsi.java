package com.aziza.santridear.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aziza.santridear.pengasuh.AdmiinActivity;
import com.aziza.santridear.R;
import com.aziza.santridear.santri.SantriActivity;

public class LoginOpsi extends AppCompatActivity {
    Button santriOpsi,pengasuhOpsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_opsi);

        santriOpsi=findViewById(R.id.santriOpsi);
        pengasuhOpsi=findViewById(R.id.pengasuhOpsi);

        String status = getIntent().getStringExtra("status");

        if (status.equals("Santri")) {
                pengasuhOpsi.setVisibility(View.GONE);
                santriOpsi.setVisibility(View.VISIBLE);
        } else {
            pengasuhOpsi.setVisibility(View.VISIBLE);
            santriOpsi.setVisibility(View.GONE);
        }

        santriOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent santri = new Intent(LoginOpsi.this, SantriActivity.class);
                startActivity(santri);
            }
        });

        pengasuhOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pengasuh = new Intent(LoginOpsi.this, AdmiinActivity.class);
                startActivity(pengasuh);
            }
        });

    }
}
