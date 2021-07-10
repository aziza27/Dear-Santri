package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.R;
import com.aziza.santridear.models.Sekolah;

import java.util.List;

public class SekolahRecyclerViewAdapter extends RecyclerView.Adapter<SekolahRecyclerViewAdapter.ViewHolder> {
    private List <Sekolah> sekolahList;
    private Context context;

    public SekolahRecyclerViewAdapter(Context context,List<Sekolah> sekolahList) {
        this.sekolahList = sekolahList;
        this.context = context;
    }

    @NonNull
    @Override
    public SekolahRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sekolah,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SekolahRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nama_absenSekolah.setText(sekolahList.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return sekolahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_absenSekolah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_absenSekolah = itemView.findViewById(R.id.nama_absenSekolah);
        }
    }
}
