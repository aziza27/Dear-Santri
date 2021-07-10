package com.aziza.santridear.AbsenActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aziza.santridear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AbsenKebersihaan extends AppCompatActivity {
    RecyclerView kebersihaan_recyclerview;
    FirebaseFirestore db;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_kebersihaan);


        setUpRecyclerView();
        setUpFireBase();
        addTestDatasToFirebase();
        loadDataFromDataBase();
    }

    private void loadDataFromDataBase() {
        db.collection("Santri").orderBy("santri").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot : task.getResult()){

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AbsenKebersihaan.this, "Problem...", Toast.LENGTH_SHORT).show();
                Log.w(".......", e.getMessage());
            }
        });
    }

    private void addTestDatasToFirebase() {
        Map<String,String> dataMapsekolah = new HashMap<>();
        dataMapsekolah.put("nama","santri");
        dataMapsekolah.put("kelas","kelas");

        db.collection("Santri")
                .add(dataMapsekolah)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                });
    }


    private void setUpFireBase() {
        db = FirebaseFirestore.getInstance();

    }

    private void setUpRecyclerView() {
        kebersihaan_recyclerview= findViewById(R.id.kebersihaan_recyclerview);
        kebersihaan_recyclerview.setHasFixedSize(false);
        kebersihaan_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

}
