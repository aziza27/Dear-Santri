package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.R;
import com.aziza.santridear.models.Hadir;
import com.aziza.santridear.models.Sekolah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SekolahRecyclerViewAdapter extends RecyclerView.Adapter<SekolahRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Sekolah> sekolahList;
    private ArrayList<Hadir> hadirList;
    private Context context;

    private HashMap<Integer, Boolean> isChecked = new HashMap<>();

    public SekolahRecyclerViewAdapter(Context context, ArrayList<Sekolah> sekolahList) {
        this.context = context;
        this.sekolahList = sekolahList;

    }

    @NonNull
    @Override
    public SekolahRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sekolah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SekolahRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nama_absenSekolah.setText(sekolahList.get(position).getSantri());
        holder.cekSekolah.setChecked(false);
        sekolahList.get(position).setPresent(false);

        if (isChecked.containsKey(position)){
            holder.cekSekolah.setChecked(isChecked.get(position));
        } else {
            holder.cekSekolah.setChecked(false);
        }

        holder.cekSekolah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sekolahList.get(position).setPresent(true);
                    Toast.makeText(context, sekolahList.get(position).getSantri()+ " checked", Toast.LENGTH_SHORT).show();
                } else {
                    sekolahList.get(position).setPresent(false);
                    Toast.makeText(context, sekolahList.get(position).getSantri()+ " unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sekolahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_absenSekolah;
        CheckBox cekSekolah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_absenSekolah = itemView.findViewById(R.id.nama_absenSekolah);
            cekSekolah = itemView.findViewById(R.id.cekSekolah);

            itemView.setOnClickListener(view -> {
                cekSekolah.setChecked(!cekSekolah.isChecked());
            });


        }
    }
}
