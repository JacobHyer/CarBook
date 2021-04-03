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

    public static void buildCardView(Car car, TextView tvTitle, TextView tvSubtitle, TextView tvCarMileage, ImageView ivCarImage) {
        // Set card title depending on whether there's a nickname
        String nickname = car.getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            //TODO: After adding Edit car, we may need tvSubtitle.setVisibilty(View.VISIBLE)
            tvTitle.setText(nickname);
            tvSubtitle.setText(car.getFormattedDesc());
        }
        else {
            tvTitle.setText(car.getFormattedDesc());
            tvSubtitle.setVisibility(View.GONE);
        }
        tvCarMileage.setText(car.getFormattedMileage());
        car.showImg(ivCarImage);
    }
}
