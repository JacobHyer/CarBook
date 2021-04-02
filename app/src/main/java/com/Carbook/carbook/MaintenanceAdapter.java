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
    private List<MaintenanceItem> maintenanceItemList;

    public MaintenanceAdapter(Context context, List<MaintenanceItem> maintenanceItemList) {
        this.maintenanceItemList = maintenanceItemList;
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
        MaintenanceItem mi = maintenanceItemList.get(position);
        holder.desc_txt.setText(mi.getDescription());
        holder.notes_txt.setText(mi.getNotes());
        holder.mileage_txt.setText(mi.getFormattedMileage());
    }

    @Override
    public int getItemCount() {
        return maintenanceItemList.size();
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
