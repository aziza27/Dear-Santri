package com.aziza.santridear.AbsenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.Sekolah;
import com.aziza.santridear.pengasuh.DatasantriActivity;
import com.aziza.santridear.pengasuh.InputDataSantri;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    int hari,bulan,tahun;
    TextView date;
    private SekolahRecyclerViewAdapter sekolahRecyclerViewAdapter;
    private Button btn_absen;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    Spinner spinner;
    String matkul;

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
        spinner = findViewById(R.id.spinner_sekolah);

        String tanggal= hari + "/" + bulan + "/" + tahun;


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                matkul = adapter.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



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
            date.setText(matkul + formattedDate);
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
                objectExample.put("msg", "Ananda " + nama +" "+ present + "di mata pelajaran " + matkul +" pada " + formattedDate);
                objectExample.put("hadir", sekolahList.get(i).getPresent());

                notif.put(matkul +formattedDate, objectExample);


                final OnSuccessListener<Void> data_added = aVoid -> {
                    Toast.makeText(AbsenSekolaah.this, "Data Added", Toast.LENGTH_SHORT).show();

                };
                final OnFailureListener data_not_added_ = e -> Toast.makeText(AbsenSekolaah.this, "Data not Added ", Toast.LENGTH_SHORT).show();
                ft.collection("notif").document(sekolahList.get(i).getKelas())
                        .set(notif, SetOptions.merge())
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

                ft.collection("absen").document("Kehadiran").collection(matkul + formattedDate).document(sekolahList.get(i).getKelas())
                        .set(hashMap)
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

            }
        });


    }

}