package com.aziza.santridear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfillActivity extends AppCompatActivity{
    private static final int GALLERY_INTENT_CODE=1023;
        TextView nama_profil,nama,nama_lengkap,kelas;
        FrameLayout beranda;
        String userId;
        DatabaseReference dr;
        FirebaseAuth auth;
        FirebaseFirestore ft;
        FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profill);

        nama = findViewById(R.id.nama);
        nama_profil = findViewById(R.id.nama_profil);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        kelas = findViewById(R.id.kelas);
        beranda = findViewById(R.id.beranda);

        auth = FirebaseAuth.getInstance();
        ft = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();


        DocumentReference dr = ft.collection("Santri").document(userId);
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                nama.setText(documentSnapshot.getString("santri"));
                nama_profil.setText(documentSnapshot.getString("santri"));
                nama_lengkap.setText(documentSnapshot.getString("santri_lengkap"));
            }
        });

        beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(ProfillActivity.this,SantriActivity.class);
                startActivity(s);
                finish();
            }
        });

    }




}



