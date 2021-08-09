package com.aziza.santridear.AbsenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.KebersihanRecyclerViewAdapter;
import com.aziza.santridear.adapter.SalatRecyclerViewAdapter;
import com.aziza.santridear.models.Kerbersihan;
import com.aziza.santridear.models.Salat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class AbsenSalat extends AppCompatActivity {
    RecyclerView salat_recyclerview;
    FirebaseFirestore db;
    int hari,bulan,tahun;
    TextView date;
    ArrayList<Salat> salatList;
    SalatRecyclerViewAdapter salatRecyclerViewAdapter;
    private Button btn_salat;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    Spinner spinner_salat;
    String salat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_salat);

        db= FirebaseFirestore.getInstance();
        salat_recyclerview = findViewById(R.id.salat_recyclerview);
        salatList = new ArrayList<>();

        btn_salat = findViewById(R.id.button_salat);
        date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        hari = calendar.get(Calendar.DAY_OF_MONTH);
        bulan = calendar.get(Calendar.MONTH);
        tahun = calendar.get(Calendar.YEAR);
        spinner_salat = findViewById(R.id.spinner_salat);

        String tanggal= hari + "/" + bulan + "/" + tahun;


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        spinner_salat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                salat = adapter.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        db.collection("data_santri")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            salatList.add(new Salat(doc.getData().get("santri") + "", doc.getData().get("uid") + "",  ""));
                            salatRecyclerViewAdapter = new SalatRecyclerViewAdapter(getBaseContext(),salatList);
                            salat_recyclerview.setHasFixedSize(false);
                            salat_recyclerview.setLayoutManager(new LinearLayoutManager(AbsenSalat.this));
                            salat_recyclerview.setAdapter(salatRecyclerViewAdapter);
                            salatRecyclerViewAdapter.notifyDataSetChanged();
                            Log.d(TAG,doc.getId()+ "=>" +doc.getData());
                        }
                    }else{
                        Log.d(TAG,"Dokumen error : ",task.getException());
                    }

                });

        btn_salat.setOnClickListener(view -> {
            date.setText(formattedDate);
            for (int i = 0; i < salatList.size(); i++) {
//                Toast.makeText(this, salatList.get(i).getSantri() + " " + salatList.get(i).getPresent(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, tanggal, Toast.LENGTH_SHORT).show();


                ft = FirebaseFirestore.getInstance();

                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("santri", salatList.get(i).getSantri());
                hashMap.put("kelas", salatList.get(i).getKelas());
                hashMap.put("hadir", salatList.get(i).getPresent());
                hashMap.put("date",tanggal);

                String hadir = salatList.get(i).getPresent();
                String present = "";
                String nama = salatList.get(i).getSantri();
                Map<String, Object> notif = new HashMap<>();
                Map<String, Object> objectExample = new HashMap<>();
                objectExample.put("title", "Kehadiran");
                objectExample.put("msg", "Ananda " + nama +" "+ hadir + "\n di Ibadah Salat " + salat +"\n pada " + formattedDate);
                objectExample.put("hadir", salatList.get(i).getPresent());

                notif.put(salat + formattedDate, objectExample);


                final OnSuccessListener<Void> data_added = aVoid -> {
                    Toast.makeText(AbsenSalat.this, "Berhasil Absen", Toast.LENGTH_SHORT).show();

                };
                final OnFailureListener data_not_added_ = e -> Toast.makeText(AbsenSalat.this, "Data not Added ", Toast.LENGTH_SHORT).show();
                ft.collection("notif").document(salatList.get(i).getKelas())
                        .set(notif, SetOptions.merge())
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

                ft.collection("absen").document("Kehadiran").collection(salat+ formattedDate).document(salatList.get(i).getKelas())
                        .set(hashMap)
                        .addOnSuccessListener(data_added)
                        .addOnFailureListener(data_not_added_);

            }
        });

    }
}
