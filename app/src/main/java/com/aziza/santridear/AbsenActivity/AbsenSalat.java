package com.aziza.santridear.AbsenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.KebersihanRecyclerViewAdapter;
import com.aziza.santridear.adapter.SalatRecyclerViewAdapter;
import com.aziza.santridear.models.Kerbersihan;
import com.aziza.santridear.models.Salat;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class AbsenSalat extends AppCompatActivity {
    RecyclerView salat_recyclerview;
    FirebaseFirestore db;
    ArrayList<Salat> salatList;
    SalatRecyclerViewAdapter salatRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_salat);

        db= FirebaseFirestore.getInstance();
        salat_recyclerview = findViewById(R.id.salat_recyclerview);
        salatList = new ArrayList<>();

        db.collection("santri")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            salatList.add(new Salat(doc.getData().get("santri")+"",doc.getData().get("kelas")+""));
                            salatRecyclerViewAdapter = new SalatRecyclerViewAdapter(getBaseContext(),salatList);
                            salat_recyclerview.setHasFixedSize(false);
                            salat_recyclerview.setLayoutManager(new LinearLayoutManager(AbsenSalat.this));
                            salat_recyclerview.setAdapter(salatRecyclerViewAdapter);
                            Log.d(TAG,doc.getId()+ "=>" +doc.getData());
                        }
                    }else{
                        Log.d(TAG,"Dokumen error : ",task.getException());
                    }

                });
    }
}
