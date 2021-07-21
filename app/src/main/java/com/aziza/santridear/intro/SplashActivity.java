package com.aziza.santridear.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.aziza.santridear.R;

public class SplashActivity extends AppCompatActivity {

    private int value;
    ProgressBar probar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        probar2=findViewById(R.id.probar2);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startProgress();
                Intent apasih = new Intent(SplashActivity.this, LoginOpsi.class);
                startActivity(apasih);
            }
        });
        thread.start();
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
