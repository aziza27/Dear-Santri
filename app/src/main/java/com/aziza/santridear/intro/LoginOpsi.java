package com.aziza.santridear.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aziza.santridear.notification.NotifReceiver;
import com.aziza.santridear.pengasuh.AdmiinActivity;
import com.aziza.santridear.R;
import com.aziza.santridear.santri.SantriActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class LoginOpsi extends AppCompatActivity {
    Button santriOpsi,pengasuhOpsi;
    SwitchMaterial sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_opsi);

        santriOpsi=findViewById(R.id.santriOpsi);
        pengasuhOpsi=findViewById(R.id.pengasuhOpsi);
        sw = findViewById(R.id.sw);



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

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // do something, the isChecked will be
            // true if the switch is in the On position
            NotifReceiver notif = new NotifReceiver();
            notif.setRepeatingAlarm(this);
        });

    }
}
