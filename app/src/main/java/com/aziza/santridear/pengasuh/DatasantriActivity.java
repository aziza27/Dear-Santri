package com.aziza.santridear.pengasuh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.aziza.santridear.R;
import com.aziza.santridear.adapter.MyRecyclerViewAdapter;
import com.aziza.santridear.models.ListSantri;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

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
                .addOnCompleteListener(task -> {
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
                });





    }


}
