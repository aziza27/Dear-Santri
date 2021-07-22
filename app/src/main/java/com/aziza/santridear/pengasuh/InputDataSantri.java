package com.aziza.santridear.pengasuh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InputDataSantri extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputEditText santri,santri_lengkap,kelas, username, password;
    Button daftar;
    ProgressBar probar1;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private DatabaseReference ref;
    String userSantri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_santri);

        santri = findViewById(R.id.santri);
        santri_lengkap = findViewById(R.id.santri_lengkap);
        kelas = findViewById(R.id.kelas);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        daftar = findViewById(R.id.daftar);
        probar1 = findViewById(R.id.probar1);

        ft = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();


        daftar.setOnClickListener(view -> {

            final String getSantri = santri.getText().toString();
            final String getSantri_lengkap = santri_lengkap.getText().toString();
            final String getKelas = kelas.getText().toString();
            final String getUsername = username.getText().toString().trim();
            final String getPassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(getSantri)){
                Toast.makeText(getApplicationContext(),"Masukkan Nama Panggil Santri !", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(getSantri_lengkap)){
                Toast.makeText(getApplicationContext(),"Masukkan Nama Lengkap Santri !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getUsername)) {
                Toast.makeText(getApplicationContext(), "Masukkan Username !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getPassword)) {
                Toast.makeText(getApplicationContext(), "Masukkan Kata Sandi !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (getPassword.length() < 6) {
                Toast.makeText(getApplicationContext(), "Kata sandi terlalu pendek, Minimal 6 karakter !", Toast.LENGTH_SHORT).show();
                return;
            }

            try{
                auth.createUserWithEmailAndPassword(getUsername, getPassword)
                        .addOnCompleteListener(InputDataSantri.this, task -> {
                            Toast.makeText(InputDataSantri.this, "Berhasil membuat akun Santri:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            probar1.setVisibility(View.GONE);
                            FirebaseAuth fa = FirebaseAuth.getInstance();
                            String uid = fa.getCurrentUser().getUid();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(InputDataSantri.this, "Gagal mendaftar." + task.getException(),
                                        Toast.LENGTH_SHORT).show();

                            } else{
                                ft = FirebaseFirestore.getInstance();
                                Map<String, Object> hashMap = new HashMap<>();
                                hashMap.put("santri", getSantri);
                                hashMap.put("santri_lengkap", getSantri_lengkap);
                                hashMap.put("kelas", getKelas);
                                hashMap.put("username", getUsername);
                                hashMap.put("password", getPassword);
                                hashMap.put("uid", uid);

                                ft.collection("data_santri").document(uid)
                                        .set(hashMap)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(InputDataSantri.this, "Data Added", Toast.LENGTH_SHORT).show();
                                            Intent dataSantri = new Intent(InputDataSantri.this,DatasantriActivity.class);
                                            startActivity(dataSantri);
                                            finish();

                                        })
                                        .addOnFailureListener(e -> Toast.makeText(InputDataSantri.this, "Data not Added ", Toast.LENGTH_SHORT).show());

                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();
            }

            probar1.setVisibility(View.VISIBLE);
            //create user


        });
}

    @Override
    protected void onResume() {
        super.onResume();
        probar1.setVisibility(View.GONE);


    }
}
