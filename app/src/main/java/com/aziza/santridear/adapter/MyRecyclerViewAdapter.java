package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.models.ListSantri;
import com.aziza.santridear.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {

    Context context;
    ArrayList<ListSantri> userArrayList;

    public MyRecyclerViewAdapter(Context context, ArrayList<ListSantri> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        holder.nama.setText(userArrayList.get(position).getSantri());
        holder.kelas.setText(userArrayList.get(position).getKelas());


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView nama,kelas;


        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            nama=itemView.findViewById(R.id.nama_santri);
            kelas=itemView.findViewById(R.id.kelas_santri);


        }
    }



}
