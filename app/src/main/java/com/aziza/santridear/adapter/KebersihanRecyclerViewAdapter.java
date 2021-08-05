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
import com.aziza.santridear.models.Kerbersihan;
import com.aziza.santridear.models.Sekolah;

import java.util.ArrayList;
import java.util.HashMap;

public class KebersihanRecyclerViewAdapter extends RecyclerView.Adapter<KebersihanRecyclerViewAdapter.ViewHolder> {
    private Context context;
   private ArrayList<Kerbersihan> kebersihanList;

    private HashMap<Integer, Boolean> isChecked = new HashMap<>();

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
        holder.cekBersih.setChecked(false);
        kebersihanList.get(position).setPresent(false);

        if (isChecked.containsKey(position)){
            holder.cekBersih.setChecked(isChecked.get(position));
        } else {
            holder.cekBersih.setChecked(false);
        }

        holder.cekBersih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    kebersihanList.get(position).setPresent(true);
                    Toast.makeText(context, kebersihanList.get(position).getSantri()+ " checked", Toast.LENGTH_SHORT).show();
                } else {
                    kebersihanList.get(position).setPresent(false);
                    Toast.makeText(context, kebersihanList.get(position).getSantri()+ " unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return kebersihanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kebersihaan;
        CheckBox cekBersih;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kebersihaan=itemView.findViewById(R.id.nama_kebersihaan);
            cekBersih=itemView.findViewById(R.id.cekBersih);

            itemView.setOnClickListener(view -> {
                cekBersih.setChecked(!cekBersih.isChecked());
            });

        }
    }
}
