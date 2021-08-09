package com.aziza.santridear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aziza.santridear.R;
import com.aziza.santridear.models.Salat;

import java.util.ArrayList;
import java.util.HashMap;

public class SalatRecyclerViewAdapter extends RecyclerView.Adapter<SalatRecyclerViewAdapter.ViewHolder> {
    String absen;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salat, parent, false);
        return new SalatRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalatRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nama_salat.setText(salatList.get(position).getSantri());
        salatList.get(position).setPresent("alpa");


        holder.salat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    pos, long id) {
                // On selecting a spinner item

                String item = parent.getItemAtPosition(pos).toString();
                salatList.get(position).setPresent(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // todo for nothing selected
            }
        });

    }

    @Override
    public int getItemCount() {
        return salatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_salat;
        Spinner salat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_salat = itemView.findViewById(R.id.nama_salat);
            salat = itemView.findViewById(R.id.spinner_shalat);



        }
    }
}
