package com.Carbook.carbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.MaintenanceViewHolder>{

    private Context context;
    private ArrayList desc, notes;

    public MaintenanceAdapter(Context context, ArrayList<String> desc, ArrayList<String> notes) {
        this.desc = desc;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MaintenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.maintenance_item, parent, false);
        return new MaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceViewHolder holder, int position) {
        holder.desc_txt.setText(String.valueOf(desc.get(position)));
        holder.notes_txt.setText(String.valueOf(notes.get(position)));

    }

    @Override
    public int getItemCount() {
        return desc.size();
    }

    public class MaintenanceViewHolder extends RecyclerView.ViewHolder {
        TextView desc_txt, notes_txt;

        public MaintenanceViewHolder(@NonNull View itemView) {
            super(itemView);
            desc_txt = itemView.findViewById(R.id.tvMaintenanceItemDesc);
            notes_txt = itemView.findViewById(R.id.tvMaintenanceItemNotes);
        }

    }
}
