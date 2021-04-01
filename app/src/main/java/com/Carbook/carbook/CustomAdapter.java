package com.Carbook.carbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<Car> carList;
//    private ArrayList id, vin, make, model, year, mileage, image;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public CustomAdapter(Context context, List<Car> carList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.carList = carList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
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
        Car c = carList.get(position);
        holder.desc_txt.setText(c.getFormattedDesc());
        holder.mileage_txt.setText(c.getFormattedMileage());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView desc_txt, mileage_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvCar);
            desc_txt = itemView.findViewById(R.id.tvCarDesc);
            mileage_txt = itemView.findViewById(R.id.tvCarMileage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
