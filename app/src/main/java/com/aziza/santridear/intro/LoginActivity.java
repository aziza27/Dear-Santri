package com.aziza.santridear.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.notification.NotifReceiver;
import com.aziza.santridear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView daftar,lupa_sandi;
    private Button login;
    private EditText username,password;
    private ProgressBar probar;
    private FirebaseAuth auth;
    private NotifReceiver notifReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        notifReceiver = new NotifReceiver();

        Intent in = getIntent();
        int getRegis = in.getIntExtra("back",0);
        if(getRegis==1){
            Toast.makeText(this, "Login kembali", Toast.LENGTH_SHORT).show();
        }
        else {
            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Login kembali", Toast.LENGTH_SHORT).show();
            }

        }

//      if((auth.getCurrentUser() != null) && saantri.isChecked()) {
////            startActivity(new Intent(MainActivity.this, UserActivity.class));
////            finish();
//        }
        setContentView(R.layout.activity_login);

        login= findViewById(R.id.login);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);
        daftar= findViewById(R.id.daftar);
        probar= findViewById(R.id.probar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String user = username.getText().toString();
            final String pass = password.getText().toString();

            if (TextUtils.isEmpty(user)) {
                Toast.makeText(getApplicationContext(), "Masukkan email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(getApplicationContext(), "Masukkan password!", Toast.LENGTH_SHORT).show();
                return;
            }

            probar.setVisibility(View.VISIBLE);

            //authenticate user
            auth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            probar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (pass.length() < 6) {
                                    password.setError(getString(R.string.minimum_password));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                notifReceiver.setRepeatingAlarm(LoginActivity.this);

                                Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                startActivity(intent);
                                finish();
                        }}
                    });
        });

        daftar.setOnClickListener(view -> {
            Intent daftar = new Intent(LoginActivity.this,RegistrasiActivity.class);
            startActivity(daftar);
        });

    }
}
