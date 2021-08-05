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
import com.aziza.santridear.models.Salat;

import java.util.ArrayList;
import java.util.HashMap;

public class SalatRecyclerViewAdapter extends RecyclerView.Adapter<SalatRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Salat> salatList;

    private HashMap<Integer, Boolean> isChecked = new HashMap<>();

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
        holder.cekSalat.setChecked(false);
        salatList.get(position).setPresent(false);

        if (isChecked.containsKey(position)){
            holder.cekSalat.setChecked(isChecked.get(position));
        } else {
            holder.cekSalat.setChecked(false);
        }

        holder.cekSalat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    salatList.get(position).setPresent(true);
                    Toast.makeText(context, salatList.get(position).getSantri()+ " checked", Toast.LENGTH_SHORT).show();
                } else {
                    salatList.get(position).setPresent(false);
                    Toast.makeText(context, salatList.get(position).getSantri()+ " unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return salatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_salat;
        CheckBox cekSalat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_salat= itemView.findViewById(R.id.nama_salat);
            cekSalat= itemView.findViewById(R.id.cekSalat);

            itemView.setOnClickListener(view -> {
                cekSalat.setChecked(!cekSalat.isChecked());
            });

        }
    }
}
