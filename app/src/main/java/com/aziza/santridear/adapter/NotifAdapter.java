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
import com.aziza.santridear.models.Notif;
import com.aziza.santridear.models.Sekolah;

import java.util.ArrayList;
import java.util.HashMap;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    private ArrayList<Notif> notifList;
    private Context context;

    private HashMap<Integer, Boolean> isChecked = new HashMap<>();

    public NotifAdapter(Context context, ArrayList<Notif> notifList) {
        this.context = context;
        this.notifList = notifList;

    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        holder.title.setText(notifList.get(position).getTitle());
        holder.msg.setText(notifList.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            msg = itemView.findViewById(R.id.msg);

            itemView.setOnClickListener(view -> {
            });


        }
    }
}
