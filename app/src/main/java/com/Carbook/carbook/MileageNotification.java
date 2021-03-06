package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * In-app notification to the user that their mileage may need to be updated.
 * Currently set to display every 30 days
 */
public class MileageNotification extends AppCompatActivity {

    private TextView carName;
    Car car;

    public static final String TAG = "MileageNotification";

    /**
     * Sets up display of notification. Size is 80% x 60% smaller than screen.
     * Loads car's information to inform user which car they will be updating
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage_notification);

        car = (Car)getIntent().getSerializableExtra("CAR");
        carName = findViewById(R.id.CarName);
        if (car.getNickname() != null) {
            carName.setText(car.getNickname());
        } else {
            carName.setText(car.getFormattedDesc());
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (int) (dm.widthPixels * 0.8);
        int height = (int) (dm.heightPixels * 0.6);

        getWindow().setLayout(width, height);
    }

    /**
     * Takes user to the update mileage screen. Estimates the current
     * mileage of the car based on the time elapsed and average miles
     * driven per week.
     * For demonstration purposes, function currently compares to a
     * static date instead of comparing to the actual date the mileage
     * was last updated
     * @param view
     * @throws ParseException
     */
    public void selectYes(View view) throws ParseException {
        Calendar dateChanged = Calendar.getInstance();
        //Actual code to pull from database is commented out for demonstration purposes
        //dateChanged.setTime(car.getMileageChanged());
        //Begin example code with fake past date
        SimpleDateFormat ex_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date example = ex_format.parse("2021-02-25 00:00:00");
        dateChanged.setTime(example);
        //End example code
        long diffDays = ((Calendar.getInstance().getTimeInMillis())
                - (dateChanged.getTimeInMillis()))
                / (24 * 60 * 60 * 1000); //converts milliseconds to days
        int estimatedMiles = (int)((car.getAvgMiles() / 7) * diffDays); //Converts car's avgMiles to avgMiles per day, then multiplies
        Intent intent = new Intent(this, MileageActivity.class);
        intent.putExtra("uniqueId", TAG);
        intent.putExtra("carId", car.getId());
        intent.putExtra(MileageActivity.EXTRA_MILEAGE, (estimatedMiles + car.getMileage()));
        intent.putExtra(MileageActivity.EXTRA_AVG_MILEAGE, car.getAvgMiles());
        startActivityForResult(intent, 2);
    }

    /**
     * Takes user back to the dashboard if they would not like to update mileage.
     * Sets the compare date for the vehicle so that the notification shows again tomorrow.
     * @param view
     */
    public void selectNo(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        //Change date of change to (today - 29) days to ensure it does not ask to be updated again until tomorrow
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -29);
        DBHelper db = new DBHelper(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        db.updateField("cars", car.getId(),"mileage_date_changed", sdf.format(c.getTime()));
        startActivity(intent);
    }

    /**
     * Callback listener. Feeds information from mileage update back to dashboard.
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == 1) {
            Intent dash = new Intent(this, DashboardActivity.class);
            Toast.makeText(this, "Mileage updated", Toast.LENGTH_SHORT).show();
            startActivity(dash);
        }
    }
}