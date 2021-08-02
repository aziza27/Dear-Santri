package com.aziza.santridear.pengasuh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.AbsenActivity.AbsenKebersihaan;
import com.aziza.santridear.AbsenActivity.AbsenSalat;
import com.aziza.santridear.AbsenActivity.AbsenSekolaah;
import com.aziza.santridear.R;
import com.aziza.santridear.TentangAplikasii;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.intro.LoginActivity;
import com.aziza.santridear.models.Sekolah;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

public class AdmiinActivity extends AppCompatActivity {

   private DrawerLayout dl_admin;
   Toolbar toolbar;
   private NavigationView nv;
   private Context context = this;
   FirebaseFirestore db = FirebaseFirestore.getInstance();

   TextView nama, namaprofil, namalengkap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admiin);

        dl_admin =findViewById(R.id.dl_admin);
        toolbar=findViewById(R.id.toolbar);
        nv=findViewById(R.id.nv_admin);
        nama = findViewById(R.id.nama);
        namaprofil = findViewById(R.id.nama_profil);
        namalengkap = findViewById(R.id.nama_lengkap);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.homei);

        FirebaseAuth fa = FirebaseAuth.getInstance();

        DocumentReference docref = db.collection("Pengasuh").document(Objects.requireNonNull(fa.getUid()));
        docref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                nama.setText(document.getString("pengasuh"));
                namaprofil.setText(document.getString("pengasuh"));
                namalengkap.setText(document.getString("pengasuh_lengkap"));


            }
        });

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profil:
                    Toast.makeText(AdmiinActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                    openActivity(ProfillActivity.class);
                    return  true;
                    case R.id.salat:
                    Toast.makeText(AdmiinActivity.this, "Absen Salat", Toast.LENGTH_SHORT).show();
                    openActivity(AbsenSalat.class);
                    return  true;
                    case R.id.absen_sekolah:
                    Toast.makeText(AdmiinActivity.this, "Absen Sekolah", Toast.LENGTH_SHORT).show();
                    openActivity(AbsenSekolaah.class);
                    return  true;
                    case R.id.absen_kebersihan: Toast.makeText(AdmiinActivity.this, "Absen Kebersihan", Toast.LENGTH_SHORT).show();
                    openActivity(AbsenKebersihaan.class);
                    return  true;
                    case R.id.data_santri:
                    Toast.makeText(AdmiinActivity.this, "Data Santri", Toast.LENGTH_SHORT).show();
                    openActivity(DatasantriActivity.class);
                    return  true;
                    case R.id.register_santri:
                    Toast.makeText(AdmiinActivity.this, "Masukan Data Santri Baru", Toast.LENGTH_SHORT).show();
                    openActivity(InputDataSantri.class);
                    return  true;
                    case R.id.tentang:
                    Toast.makeText(AdmiinActivity.this, "Tentang", Toast.LENGTH_SHORT).show();
                    openActivity(TentangAplikasii.class);
                    return  true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                    Toast.makeText(AdmiinActivity.this, "Keluar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdmiinActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    return  true;


                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                dl_admin.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openActivity(Class c ) {
        startActivity(new Intent(context, c));
    }


}
