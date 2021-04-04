package com.Carbook.carbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder> {
    private Context context;
    private List<Car> carList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public CarsAdapter(Context context, List<Car> carList, RecyclerViewClickInterface recyclerViewClickInterface) {
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
        TextView tvTitle =  holder.itemView.findViewById(R.id.tvTitle);
        TextView tvSubtitle = holder.itemView.findViewById(R.id.tvSubtitle);
        TextView tvCarMileage = holder.itemView.findViewById(R.id.tvCarMileage);
        ImageView ivCarImage = holder.itemView.findViewById(R.id.ivCarImage);
        CarbookUtil.buildCardView(carList.get(position), tvTitle, tvSubtitle, tvCarMileage, ivCarImage);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvCar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
