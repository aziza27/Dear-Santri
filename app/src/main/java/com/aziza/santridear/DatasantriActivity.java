package com.aziza.santridear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.aziza.santridear.AbsenActivity.AbsenSekolaah;
import com.aziza.santridear.adapter.MyRecyclerViewAdapter;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.ListSantri;
import com.aziza.santridear.models.Santri;
import com.aziza.santridear.models.Sekolah;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aziza.santridear.InputDataSantri.TAG;

public class DatasantriActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<ListSantri> santriArrayList;
    MyRecyclerViewAdapter myRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasantri);

        db = FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.recyclerview);
        santriArrayList= new ArrayList<>();

        db.collection("santri")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                santriArrayList.add(new ListSantri(document.getData().get("santri")+"", document.getData().get("kelas")+""));
                                myRecyclerViewAdapter= new MyRecyclerViewAdapter(DatasantriActivity.this, santriArrayList);
                                recyclerView.setHasFixedSize(false);
                                recyclerView.setLayoutManager(new LinearLayoutManager(DatasantriActivity.this));
                                recyclerView.setAdapter(myRecyclerViewAdapter);

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });





    }


}
