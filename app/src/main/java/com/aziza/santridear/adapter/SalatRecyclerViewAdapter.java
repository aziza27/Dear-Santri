package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.R;
import com.aziza.santridear.models.Salat;

import java.util.ArrayList;

public class SalatRecyclerViewAdapter extends RecyclerView.Adapter<SalatRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Salat> salatList;

    public SalatRecyclerViewAdapter(Context context, ArrayList<Salat> salatList) {
        this.context = context;
        this.salatList = salatList;
    }

    @NonNull
    @Override
    public SalatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salat,parent,false);
        return new SalatRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalatRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nama_salat.setText(salatList.get(position).getSantri());

    }

    @Override
    public int getItemCount() {
        return salatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_salat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_salat= itemView.findViewById(R.id.nama_salat);
        }
    }
}
