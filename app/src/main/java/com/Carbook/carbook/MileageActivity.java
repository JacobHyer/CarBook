
package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MileageActivity extends AppCompatActivity {


    private EditText userMileage;
    private EditText userAvg;
    private DBHelper db;
    private int miles;
    private int milesAvg;
    private String calledBy;
    private long carId;

    public static final String EXTRA_MILEAGE = "MILEAGE";
    public static final String EXTRA_AVG_MILEAGE = "AVG_MILEAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        Intent intent = getIntent();
        //Used to identify if the activity was started from a notification or from an activity
        calledBy = intent.getStringExtra("uniqueId");
        carId = intent.getLongExtra("carId", -1);
        miles = intent.getIntExtra(EXTRA_MILEAGE, 0);
        milesAvg = intent.getIntExtra(EXTRA_AVG_MILEAGE, 0);


        db = new DBHelper(this);

        userMileage = findViewById(R.id.user_mileage);
        userAvg = findViewById(R.id.user_avg);

        // Set existing mileage values if called from ViewCarActivity or MileageNotification
        if(calledBy != null && (calledBy.equals(ViewCarActivity.TAG) || calledBy.equals(MileageNotification.TAG))) {
            userMileage.setText(String.valueOf(miles));
            userAvg.setText(String.valueOf(milesAvg));
        }

        userMileage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    miles = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    // Exceptions being thrown on empty strings
                }
            }
        });

        userAvg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    milesAvg = Integer.parseInt(s.toString());
                } catch (Exception e) {
                // Exceptions being thrown on empty strings
                }
            }
        });
    }

    public void saveMileage(View view) {
        //TODO: We may want to consolidate all code into this format to have this activity update the databases, then create the intent specific to which activity needs to be opened (based on request codes?)
        //Request code 2 signifies that saving will need to return the user to the DashboardActivity
        Intent intent = null;
        if (calledBy != null && calledBy == MileageNotification.TAG) {
            intent = new Intent(this, MileageNotification.class);
        } else if (calledBy != null && calledBy == ViewCarActivity.TAG) {
            intent = new Intent(this, ViewCarActivity.class);
        } else if (calledBy != null && calledBy == AddCarActivity.TAG) {
            //Will finish and send back to AddCarActivity since there is no entry in the database for the new car
            intent = new Intent(this, AddCarActivity.class);
            intent.putExtra(EXTRA_MILEAGE, miles);
            intent.putExtra(EXTRA_AVG_MILEAGE, milesAvg);
            setResult(1, intent);
            finish();
        }
        Boolean success = db.updateField("cars", carId, "mileage", String.valueOf(miles));
        if(success) success = db.updateField("cars", carId, "avg_miles", String.valueOf(milesAvg));
        if (success) {
            //Result code 1 signifies that the update was successful
            setResult(1, intent);
            finish();
        } else {
            //Result code 0 signifies that the update failed
            setResult(0, intent);
        }
    }

    public void cancelMileage(View view) {
        //TODO: Fix return to dashboard from notification
        Intent intent = new Intent(this, AddCarActivity.class);
        setResult(0, intent);
        finish();
    }
}