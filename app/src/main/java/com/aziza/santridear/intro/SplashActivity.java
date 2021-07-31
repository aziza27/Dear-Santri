package com.aziza.santridear.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.aziza.santridear.AbsenActivity.AbsenSekolaah;
import com.aziza.santridear.R;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.Sekolah;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

public class SplashActivity extends AppCompatActivity {

    private int value;
    ProgressBar probar2;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        probar2=findViewById(R.id.probar2);
        startProgress();

        DocumentReference docref = ft.collection("Pengasuh").document(mAuth.getUid());
        docref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    status = document.getString("status");
                    Intent apasih = new Intent(SplashActivity.this, LoginOpsi.class);
                    apasih.putExtra("status", status);
                    startActivity(apasih);
                }
            }
        });

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                startProgress();
//                Intent apasih = new Intent(SplashActivity.this, LoginOpsi.class);
//                startActivity(apasih);
//            }
//        });
//        thread.start();
    }

    private void startProgress() {
        for(value=0;value<=100;value=value+1){
                try {
                    Thread.sleep(50);
                    probar2.setProgress(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



                //                // to do auto generated stub
//                Intent apasih = new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(apasih);
//                // jeda setelah splashscren
//
//            }
//        },waktu);
