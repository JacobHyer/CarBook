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
    //TODO: Convert these ArrayLists to Maintenance objects instead (like we did in Dashboard / CustomAdapter with Cars)
    private List desc, notes, mileage, date_maintenance, car_id;

    public MaintenanceAdapter(Context context, List<String> desc, List<String> notes, List<Integer> mileage, List<String>date_maintenance, List<Integer>car_id) {
        this.desc = desc;
        this.notes = notes;
        this.mileage = mileage;
        this.date_maintenance = date_maintenance;
        this.car_id = car_id;
    }

    @NonNull
    @Override
    public MaintenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.maintenance_item, parent, false);
        return new MaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceViewHolder holder, int position) {
        holder.desc_txt.setText(String.valueOf(desc.get(position)));
        holder.notes_txt.setText(String.valueOf(notes.get(position)));
        holder.mileage_txt.setText(String.valueOf(mileage.get(position)));
    }

    @Override
    public int getItemCount() {
        return desc.size();
    }

    public class MaintenanceViewHolder extends RecyclerView.ViewHolder {
        TextView desc_txt, notes_txt, mileage_txt;

        public MaintenanceViewHolder(@NonNull View itemView) {
            super(itemView);
            desc_txt = itemView.findViewById(R.id.tvMaintenanceItemDesc);
            notes_txt = itemView.findViewById(R.id.tvMaintenanceItemNotes);
            mileage_txt = itemView.findViewById(R.id.tvMaintenanceItemMileage);
        }

    }
}
