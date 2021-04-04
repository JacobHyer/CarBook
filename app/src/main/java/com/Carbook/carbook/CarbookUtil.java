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

    /**
     * Inserts a Car's display data into an instance of cv_car.xml.
     * Formats the card depending on whether or not the Car has a nickname.
     * @param car the Car
     * @param tvTitle the card's title field
     * @param tvSubtitle the card's subtitle field
     * @param tvCarMileage the card's mileage field
     * @param ivCarImage the card's image field
     */
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
