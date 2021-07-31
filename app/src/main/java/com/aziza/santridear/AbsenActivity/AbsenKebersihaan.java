package com.aziza.santridear.AbsenActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.KebersihanRecyclerViewAdapter;
import com.aziza.santridear.models.Kerbersihan;
import com.aziza.santridear.models.Sekolah;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class AbsenKebersihaan extends AppCompatActivity {
    RecyclerView kebersihaan_recyclerview;
    FirebaseFirestore db;
    int hari,bulan,tahun;
    TextView date;
    ArrayList<Kerbersihan> kebersihanList;
    KebersihanRecyclerViewAdapter kebersihanRecyclerViewAdapter;
    private Button btn_bersih;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    Spinner spinner_bersih;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_kebersihaan);

        db=FirebaseFirestore.getInstance();
        kebersihaan_recyclerview=findViewById(R.id.kebersihaan_recyclerview);
        kebersihanList = new ArrayList<>();

        btn_bersih = findViewById(R.id.btn_bersih);
        Calendar calendar = Calendar.getInstance();
        hari = calendar.get(Calendar.DAY_OF_MONTH);
        bulan = calendar.get(Calendar.MONTH);
        tahun = calendar.get(Calendar.YEAR);
        spinner_bersih = findViewById(R.id.spinner_bersih);

        String tanggal= hari + "/" + bulan + "/" + tahun;


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String bersih = spinner_bersih.getSelectedItem().toString();


        db.collection("santri")
                .get()
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    kebersihanList.add(new Kerbersihan(documentSnapshot.getData().get("santri") + "", documentSnapshot.getData().get("uid") + "",  false));
                    kebersihanRecyclerViewAdapter = new KebersihanRecyclerViewAdapter(getBaseContext(),kebersihanList);
                    kebersihaan_recyclerview.setHasFixedSize(false);
                    kebersihaan_recyclerview.setLayoutManager(new LinearLayoutManager(AbsenKebersihaan.this));
                    kebersihaan_recyclerview.setAdapter(kebersihanRecyclerViewAdapter);
                    kebersihanRecyclerViewAdapter.notifyDataSetChanged();
                    Log.d(TAG,documentSnapshot.getId()+ "=>" +documentSnapshot.getData());
                }
            }else{
                Log.d(TAG,"Dokumen error : ",task.getException());
            }
        });

        btn_bersih.setOnClickListener(view -> {
            date.setText(formattedDate);
            for (int i = 0; i < kebersihanList.size(); i++) {
                Toast.makeText(this, kebersihanList.get(i).getSantri() + " " + kebersihanList.get(i).getPresent(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, tanggal, Toast.LENGTH_SHORT).show();


                ft = FirebaseFirestore.getInstance();

                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("santri", kebersihanList.get(i).getSantri());
                hashMap.put("kelas", kebersihanList.get(i).getKelas());
                hashMap.put("hadir", kebersihanList.get(i).getPresent());
                hashMap.put("date",tanggal);

                Boolean hadir = kebersihanList.get(i).getPresent();
                String present = "";
                String nama = kebersihanList.get(i).getSantri();
                if (hadir){
                    present = "Hadir";
                } else {
                    present = "Tidak Hadir";
                }
                Map<String, Object> notif = new HashMap<>();
                Map<String, Object> objectExample = new HashMap<>();
                objectExample.put("title", "Kehadiran");
                objectExample.put("msg", "Ananda " + nama +" "+ present + "di mata pelajaran " + bersih +" pada " + formattedDate);
                objectExample.put("hadir", kebersihanList.get(i).getPresent());

                notif.put(formattedDate, objectExample);


                final OnSuccessListener<Void> data_added = aVoid -> {
                    Toast.makeText(AbsenKebersihaan.this, "Data Added", Toast.LENGTH_SHORT).show();

                };
                final OnFailureListener data_not_added_ = e -> Toast.makeText(AbsenKebersihaan.this, "Data not Added ", Toast.LENGTH_SHORT).show();
                ft.collection("notif").document(kebersihanList.get(i).getKelas())
                        .set(notif, SetOptions.merge())
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

                ft.collection("absen").document("Kehadiran").collection(formattedDate).document(kebersihanList.get(i).getKelas())
                        .set(hashMap)
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

            }
        });




    }

}
