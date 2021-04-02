
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
    private int code;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        Intent intent = getIntent();
        //Used to identify if the activity was started from a notification or from an activity
        code = intent.getIntExtra("REQUESTCODE", -1);
        id = intent.getLongExtra("ID", -1);
        db = new DBHelper(this);

        userMileage = findViewById(R.id.user_mileage);
        userAvg = findViewById(R.id.user_avg);

        // Set existing mileage values if called from ViewCarActivity
        String calledBy = getIntent().getStringExtra("uniqueId");
        if(calledBy != null && calledBy.equals(ViewCarActivity.TAG)) {
            miles = getIntent().getIntExtra(ViewCarActivity.EXTRA_MILEAGE, 0);
            milesAvg = getIntent().getIntExtra(ViewCarActivity.EXTRA_AVG_MILEAGE, 0);
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
        if (code == 2) {
            Boolean success = db.updateField("cars", id, "mileage", String.valueOf(miles));
            if(success) success = db.updateField("cars", id, "avg_miles", String.valueOf(milesAvg));
            if (success) {
                Intent intent = new Intent(this, MileageNotification.class);
                setResult(2, intent);
                finish();
            }
        } else {
            Intent intent = new Intent(this,AddCarActivity.class);
            intent.putExtra("MILEAGE", miles);
            intent.putExtra("AVG_MILEAGE", milesAvg);
            setResult(1, intent);
            finish();
        }
    }

    public void cancelMileage(View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        setResult(0, intent);
        finish();
    }
}