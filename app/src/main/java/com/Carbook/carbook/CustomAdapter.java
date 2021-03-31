package com.Carbook.carbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, vin, make, model, year, mileage, image;

    CustomAdapter(Context context,
                  ArrayList id,
                  ArrayList make,
                  ArrayList model,
                  ArrayList year,
                  ArrayList mileage) {
        this.context = context;
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
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
        String carDesc = year.get(position) + " " + make.get(position) + " " + model.get(position);
        holder.desc_txt.setText(carDesc);
        holder.mileage_txt.setText(String.valueOf(mileage.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView desc_txt, mileage_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvCar);
            desc_txt = itemView.findViewById(R.id.tvCarDesc);
            mileage_txt = itemView.findViewById(R.id.tvCarMileage);
        }
    }
}
