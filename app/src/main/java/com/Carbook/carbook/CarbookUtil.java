package com.Carbook.carbook;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A utility class for commonly used functions in Carbook
 */
public class CarbookUtil {

    public CarbookUtil() {

    }

    public static void buildCardView(Activity activity, Car car) {
        TextView tvTitle = activity.findViewById(R.id.tvTitle);
        TextView tvSubtitle = activity.findViewById(R.id.tvSubtitle);
        TextView tvCarMileage = activity.findViewById(R.id.tvCarMileage);
        ImageView ivCarImage = activity.findViewById(R.id.ivCarImage);

        // Set card title depending on whether there's a nickname
        String nickname = car.getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            tvTitle.setText(nickname);
            tvSubtitle.setText(car.getFormattedDesc());
        }
        else {
            tvTitle.setText(car.getFormattedDesc());
        }
        tvCarMileage.setText(car.getFormattedMileage());
        car.showImg(ivCarImage);
    }

    public static void buildCardView(RecyclerView.ViewHolder holder, Car car) {

        //TODO: DIS GUY RIGHT HURR
        TextView tvTitle =  holder.itemView.findViewById(R.id.tvTitle);
        TextView tvSubtitle = holder.itemView.findViewById(R.id.tvSubtitle);
        TextView tvCarMileage = holder.itemView.findViewById(R.id.tvCarMileage);
        ImageView ivCarImage = holder.itemView.findViewById(R.id.ivCarImage);

        // Set card title depending on whether there's a nickname
        String nickname = car.getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            tvTitle.setText(nickname);
            tvSubtitle.setText(car.getFormattedDesc());
        }
        else {
            tvTitle.setText(car.getFormattedDesc());
        }
        tvCarMileage.setText(car.getFormattedMileage());
        car.showImg(ivCarImage);
    }
}
