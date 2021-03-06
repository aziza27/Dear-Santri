package com.aziza.santridear.santri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aziza.santridear.R;
import com.aziza.santridear.TentangAplikasii;
import com.aziza.santridear.intro.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class SantriActivity extends AppCompatActivity {
    CardView cardLogout,cardProfil,cardNotif,cardTentang;
    TextView namaSantri,kelasSantri;
    String userId;
    FirebaseAuth auth;
    FirebaseFirestore ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santri);

        auth = FirebaseAuth.getInstance();
        ft = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();


        DocumentReference dr = ft.collection("data_santri").document(Objects.requireNonNull(userId));
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                namaSantri.setText(document.getString("santri_lengkap"));
                kelasSantri.setText(document.getString("kelas"));
            }
        });

        init();
        cardFunction();


    }

    private void cardFunction() {
        cardLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SantriActivity.this, LoginActivity.class));
        });

        cardTentang.setOnClickListener(view -> {

            Intent tentang = new Intent(SantriActivity.this, TentangAplikasii.class);
            startActivity(tentang);
        });

        cardProfil.setOnClickListener(view -> {
            Intent profil = new Intent(SantriActivity.this,ProfilSantri.class);
            startActivity(profil);
        });

        cardNotif.setOnClickListener(view -> {
            Intent notif = new Intent(SantriActivity.this, NotifikasiActivity.class);
            startActivity(notif);

        });


    }

    private void init(){
        cardLogout = findViewById(R.id.crdLogout);
        cardNotif = findViewById(R.id.crdNotif);
        cardProfil = findViewById(R.id.crdProfil);
        cardTentang = findViewById(R.id.crdTentang);
        namaSantri=findViewById(R.id.namaSantri);
        kelasSantri=findViewById(R.id.kelasSantri);


    }
}
