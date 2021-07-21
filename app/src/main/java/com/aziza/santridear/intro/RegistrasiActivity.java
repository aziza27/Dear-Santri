package com.aziza.santridear.intro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aziza.santridear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputEditText pengasuh,pengasuh_lengkap, username, password;
    Button daftar;
    ProgressBar probar1;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private DatabaseReference ref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);


        pengasuh = findViewById(R.id.pengasuh);
        pengasuh_lengkap = findViewById(R.id.pengasuh_lengkap);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        daftar = findViewById(R.id.daftar);
        probar1 = findViewById(R.id.probar1);

        ft = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();


        daftar.setOnClickListener(view -> {

            final String getPengasuh = pengasuh.getText().toString();
            final String getPengasuh_lengkap = pengasuh_lengkap.getText().toString();
            final String getUsername = username.getText().toString().trim();
            final String getPassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(getPengasuh)){
                Toast.makeText(getApplicationContext(),"Masukkan Nama Panggil Pengasuh !", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(getPengasuh_lengkap)){
                Toast.makeText(getApplicationContext(),"Masukkan Nama Lengkap Pengasuh !", Toast.LENGTH_SHORT).show();
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


            probar1.setVisibility(View.VISIBLE);
            //create user

            auth.createUserWithEmailAndPassword(getUsername, getPassword)
                    .addOnCompleteListener(RegistrasiActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegistrasiActivity.this, "Berhasil membuat akun pengasuh:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            probar1.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegistrasiActivity.this, "Gagal mendaftar." + task.getException(),
                                        Toast.LENGTH_SHORT).show();

                            } else{



//                                auth.signInWithEmailAndPassword(getUsername, getPassword)
//                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                 if (task.isSuccessful()) {
                                                    Log.d("signin", "Login Sukses");
                                                    ft = FirebaseFirestore.getInstance();
                                                    Map<String, Object> datauser = new HashMap<>();
                                                    datauser.put("pengasuh", getPengasuh);
                                                    datauser.put("pengasuh_lengkap", getPengasuh_lengkap);
                                                    datauser.put("username", getUsername);
                                                    datauser.put("password", getPassword);


                                                    ft.collection("Santri").document(getPengasuh_lengkap).set(datauser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(RegistrasiActivity.this, "Anda Berhasil", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(RegistrasiActivity.this, "Anda Gagal", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent i = new Intent(RegistrasiActivity.this, LoginActivity.class);
                                                    i.putExtra("back", 1);
                                                    startActivity(i);
                                                    finish();

//                                                }
//                                            }
//                                        });
                            }
                        }
                    });
    });

}

    @Override
    protected void onResume() {
        super.onResume();
        probar1.setVisibility(View.GONE);
    }

}

