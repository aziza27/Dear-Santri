package com.aziza.santridear.AbsenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.Hadir;
import com.aziza.santridear.models.Sekolah;
import com.aziza.santridear.pengasuh.DatasantriActivity;
import com.aziza.santridear.pengasuh.InputDataSantri;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

public class AbsenSekolaah extends AppCompatActivity {
    private RecyclerView sekolaah_recyclerview;
    FirebaseFirestore db;
    ArrayList<Sekolah> sekolahList;
    ArrayList<Hadir> hadirs;
    int hari,bulan,tahun;
    TextView date;
    private SekolahRecyclerViewAdapter sekolahRecyclerViewAdapter;
    private Button btn_absen;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_sekolaah);

        db = FirebaseFirestore.getInstance();
        sekolaah_recyclerview = findViewById(R.id.sekolaah_recyclerview);
        sekolahList = new ArrayList<>();
        btn_absen = findViewById(R.id.button_sekolah);
        date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        hari = calendar.get(Calendar.DAY_OF_MONTH);
        bulan = calendar.get(Calendar.MONTH);
        tahun = calendar.get(Calendar.YEAR);

        String tanggal= hari + "/" + bulan + "/" + tahun;


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);


        db.collection("data_santri")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            sekolahList.add(new Sekolah(document.getData().get("santri") + "", document.getData().get("uid") + "",  false));
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
            date.setText(formattedDate);
            for (int i = 0; i < sekolahList.size(); i++) {
                Toast.makeText(this, sekolahList.get(i).getSantri() + " " + sekolahList.get(i).getPresent(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, tanggal, Toast.LENGTH_SHORT).show();


                ft = FirebaseFirestore.getInstance();

                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("santri", sekolahList.get(i).getSantri());
                hashMap.put("kelas", sekolahList.get(i).getKelas());
                hashMap.put("hadir", sekolahList.get(i).getPresent());
                hashMap.put("date",tanggal);

                Boolean hadir = sekolahList.get(i).getPresent();
                String present = "";
                String nama = sekolahList.get(i).getSantri();
                if (hadir){
                    present = "Hadir";
                } else {
                    present = "Tidak Hadir";
                }
                Map<String, Object> notif = new HashMap<>();
                Map<String, Object> objectExample = new HashMap<>();
                objectExample.put("title", "Kehadiran");
                objectExample.put("msg", "Ananda " + nama +" "+ present + " pada " + formattedDate);
                objectExample.put("hadir", sekolahList.get(i).getPresent());

                notif.put(formattedDate, objectExample);




                ft.collection("notif").document(sekolahList.get(i).getKelas())
                        .set(notif, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AbsenSekolaah.this, "Data Added", Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(e -> Toast.makeText(AbsenSekolaah.this, "Data not Added ", Toast.LENGTH_SHORT).show());

                ft.collection("santri").document("Kehadiran").collection(formattedDate).document(sekolahList.get(i).getKelas())
                        .set(hashMap)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AbsenSekolaah.this, "Data Added", Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(e -> Toast.makeText(AbsenSekolaah.this, "Data not Added ", Toast.LENGTH_SHORT).show());

            }
        });


    }
}