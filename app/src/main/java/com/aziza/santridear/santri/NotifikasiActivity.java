 package com.aziza.santridear.santri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aziza.santridear.notification.NotifReceiver;
import com.aziza.santridear.R;

 public class NotifikasiActivity extends AppCompatActivity {

     private NotifReceiver notifReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

    }
}
