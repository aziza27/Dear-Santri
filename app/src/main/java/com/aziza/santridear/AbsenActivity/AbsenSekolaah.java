package com.aziza.santridear.AbsenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.Hadir;
import com.aziza.santridear.models.Sekolah;
import com.aziza.santridear.pengasuh.DatasantriActivity;
import com.aziza.santridear.pengasuh.InputDataSantri;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

public class AbsenSekolaah extends AppCompatActivity {
    private RecyclerView sekolaah_recyclerview;
    FirebaseFirestore db;
    ArrayList<Sekolah> sekolahList;
    ArrayList<Hadir> hadirs;
    private SekolahRecyclerViewAdapter sekolahRecyclerViewAdapter;
    private Button btn_absen;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_sekolaah);

        db = FirebaseFirestore.getInstance();
        sekolaah_recyclerview = findViewById(R.id.sekolaah_recyclerview);
        sekolahList = new ArrayList<>();
        btn_absen = findViewById(R.id.button_sekolah);


        db.collection("santri")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            sekolahList.add(new Sekolah(document.getData().get("santri") + "", document.getData().get("kelas") + "", false));
                            sekolahRecyclerViewAdapter = new SekolahRecyclerViewAdapter(getBaseContext(), sekolahList);
                            sekolaah_recyclerview.setHasFixedSize(false);
                            sekolaah_recyclerview.setLayoutManager(new LinearLayoutManager(AbsenSekolaah.this));
                            sekolaah_recyclerview.setAdapter(sekolahRecyclerViewAdapter);
                            sekolahRecyclerViewAdapter.notifyDataSetChanged();
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });


        btn_absen.setOnClickListener(view -> {
            for (int i = 0; i < sekolahList.size(); i++) {
                Toast.makeText(this, sekolahList.get(i).getSantri() + " " + sekolahList.get(i).getPresent(), Toast.LENGTH_SHORT).show();


            ft = FirebaseFirestore.getInstance();
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("santri", sekolahList.get(i).getSantri());
            hashMap.put("kelas", sekolahList.get(i).getKelas());
            hashMap.put("hadir", sekolahList.get(i).getPresent());


            ft.collection("Kehadiran").document(sekolahList.get(i).getSantri())
                    .set(hashMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AbsenSekolaah.this, "Data Added", Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener(e -> Toast.makeText(AbsenSekolaah.this, "Data not Added ", Toast.LENGTH_SHORT).show());

            }
        });


    }
}
