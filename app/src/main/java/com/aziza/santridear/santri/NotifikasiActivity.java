package com.aziza.santridear.santri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aziza.santridear.AbsenActivity.AbsenSekolaah;
import com.aziza.santridear.adapter.NotifAdapter;
import com.aziza.santridear.adapter.SekolahRecyclerViewAdapter;
import com.aziza.santridear.models.Notif;
import com.aziza.santridear.models.Sekolah;
import com.aziza.santridear.notification.NotifReceiver;
import com.aziza.santridear.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.aziza.santridear.pengasuh.InputDataSantri.TAG;

public class NotifikasiActivity extends AppCompatActivity {

    private RecyclerView notifRecycler;
    NotifAdapter notifAdapter;
    FirebaseFirestore db;
    ArrayList<Notif> notifList;
    TextView textNotif;
    private FirebaseFirestore ft = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        db = FirebaseFirestore.getInstance();
        textNotif = findViewById(R.id.textNotif);
        notifRecycler = findViewById(R.id.notif_recycler);
        notifList = new ArrayList<>();

        db.collection("notif").document(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document != null) {

                            Map<String, Object> map = document.getData();
                            if (map != null) {
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    textNotif.setVisibility(View.GONE);

                                    String key = entry.getKey();
                                    Object value = entry.getValue();

                                    if (value instanceof Map) {
                                        Map maps = (Map) value; //If you know the generic type you could use Map<String, Object> etc. instead
                                        String msg = (String) maps.get("msg");
                                        String title = (String) maps.get("title");

                                        notifList.add(new Notif(title, msg));
                                        notifAdapter = new NotifAdapter(getBaseContext(), notifList);
                                        notifRecycler.setHasFixedSize(false);
                                        notifRecycler.setLayoutManager(new LinearLayoutManager(NotifikasiActivity.this));
                                        notifRecycler.setAdapter(notifAdapter);
                                        notifAdapter.notifyDataSetChanged();
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                }
                            } else {
                                textNotif.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                });

    }
}
