package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.R;
import com.aziza.santridear.models.Kerbersihan;
import com.aziza.santridear.models.Sekolah;

import java.util.ArrayList;

public class KebersihanRecyclerViewAdapter extends RecyclerView.Adapter<KebersihanRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Kerbersihan> kebersihanList;

    public KebersihanRecyclerViewAdapter(Context context, ArrayList<Kerbersihan> kebersihanList) {
        this.context = context;
        this.kebersihanList = kebersihanList;
    }

    @NonNull
    @Override
    public KebersihanRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kebesihan,parent,false);
        return new KebersihanRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KebersihanRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nama_kebersihaan.setText(kebersihanList.get(position).getSantri());

    }

    @Override
    public int getItemCount() {
        return kebersihanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kebersihaan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kebersihaan=itemView.findViewById(R.id.nama_kebersihaan);
        }
    }
}
