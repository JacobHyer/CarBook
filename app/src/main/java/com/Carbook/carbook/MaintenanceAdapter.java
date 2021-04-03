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
    private List<MaintenanceItem> maintenanceItemList;
    private RecyclerViewClickInterface rvci;

    public MaintenanceAdapter(Context context, List<MaintenanceItem> maintenanceItemList, RecyclerViewClickInterface rvci) {
        this.context = context;
        this.maintenanceItemList = maintenanceItemList;
        this.rvci = rvci;
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
        holder.date_text.setText(mi.getFormattedDate());
    }

    @Override
    public int getItemCount() {
        return maintenanceItemList.size();
    }

    public class MaintenanceViewHolder extends RecyclerView.ViewHolder {
        TextView desc_txt, notes_txt, mileage_txt, date_text;

        public MaintenanceViewHolder(@NonNull View itemView) {
            super(itemView);
            desc_txt = itemView.findViewById(R.id.tvMaintenanceItemDesc);
            notes_txt = itemView.findViewById(R.id.tvMaintenanceItemNotes);
            mileage_txt = itemView.findViewById(R.id.tvMaintenanceItemMileage);
            date_text = itemView.findViewById(R.id.tvMaintenanceItemDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rvci.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    rvci.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }

    }
}
