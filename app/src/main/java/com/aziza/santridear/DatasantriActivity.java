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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasantriActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<ListSantri> userArrayList;
    MyRecyclerViewAdapter myRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasantri);

        db = FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.recyclerview);




        userArrayList= new ArrayList<>();
//        myRecyclerViewAdapter= new MyRecyclerViewAdapter(getBaseContext(),userArrayList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);
//
//        db.collection("Pengasuh").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                for (DocumentChange doc: value.getDocumentChanges()){
//                    if(doc.getType()== DocumentChange.Type.ADDED){
//                        ListSantri listSantri = doc.getDocument().toObject(ListSantri.class);
//                        userArrayList.add(listSantri);
//                        myRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
        db.collection("Santri").orderBy("santri").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot : task.getResult()){
                            ListSantri listSantri = new ListSantri(querySnapshot.getString("santri"),
                                    (querySnapshot.getString("kelas")));
                            userArrayList.add(listSantri);
                        }
                        myRecyclerViewAdapter= new MyRecyclerViewAdapter(DatasantriActivity.this,userArrayList);
                        recyclerView.setAdapter(myRecyclerViewAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DatasantriActivity.this, "Problem...", Toast.LENGTH_SHORT).show();
                Log.w(".......", e.getMessage());
            }
        });




    }


}
