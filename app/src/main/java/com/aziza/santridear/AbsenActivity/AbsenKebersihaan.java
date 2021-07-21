package com.aziza.santridear.AbsenActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class AbsenKebersihaan extends AppCompatActivity {
    RecyclerView kebersihaan_recyclerview;
    FirebaseFirestore db;
    ArrayList<Kerbersihan> kebersihanList;
    KebersihanRecyclerViewAdapter kebersihanRecyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_kebersihaan);

        db=FirebaseFirestore.getInstance();
        kebersihaan_recyclerview=findViewById(R.id.kebersihaan_recyclerview);
        kebersihanList = new ArrayList<>();


        db.collection("santri")
                .get()
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    kebersihanList.add(new Kerbersihan(documentSnapshot.getData().get("santri")+"",documentSnapshot.getData().get("kelas")+""));
                    kebersihanRecyclerViewAdapter = new KebersihanRecyclerViewAdapter(getBaseContext(),kebersihanList);
                    kebersihaan_recyclerview.setHasFixedSize(false);
                    kebersihaan_recyclerview.setLayoutManager(new LinearLayoutManager(AbsenKebersihaan.this));
                    kebersihaan_recyclerview.setAdapter(kebersihanRecyclerViewAdapter);
                    Log.d(TAG,documentSnapshot.getId()+ "=>" +documentSnapshot.getData());
                }
            }else{
                Log.d(TAG,"Dokumen error : ",task.getException());
            }
        });



    }

}
