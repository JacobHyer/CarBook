package com.Carbook.carbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, vin, make, model, year, mileage, image;

    CustomAdapter(Context context,
                  ArrayList id,
                  ArrayList make,
                  ArrayList model,
                  ArrayList mileage) {
        this.context = context;
        this.id = id;
        this.make = make;
        this.model = model;
        this.mileage = mileage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.make_txt.setText(String.valueOf(make.get(position)));
        holder.model_txt.setText(String.valueOf(model.get(position)));
        holder.mileage_txt.setText(String.valueOf(mileage.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_txt, make_txt, model_txt, mileage_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            make_txt = itemView.findViewById(R.id.make_txt);
            model_txt = itemView.findViewById(R.id.model_txt);
            mileage_txt = itemView.findViewById(R.id.mileage_txt);
        }
    }
}
