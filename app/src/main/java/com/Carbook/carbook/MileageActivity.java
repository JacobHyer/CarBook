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
    private int miles;
    private int milesAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        userMileage = findViewById(R.id.user_mileage);
        userAvg = findViewById(R.id.user_avg);

        // Set existing mileage values if called from ViewCarActivity
        String calledBy = getIntent().getStringExtra("uniqueId");
        if(calledBy != null && calledBy.equals(ViewCarActivity.TAG)) {
//            userMileage.setText(getIntent().getStringExtra(ViewCarActivity.EXTRA_MILEAGE));
            userMileage.setText("please work");
            userAvg.setText(getIntent().getStringExtra(ViewCarActivity.EXTRA_AVG_MILEAGE));

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
                miles = Integer.parseInt(s.toString());
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
                milesAvg = Integer.parseInt(s.toString());
            }
        });
    }

    public void saveMileage(View view) {
        Intent intent = new Intent(this,AddCarActivity.class);
        intent.putExtra("MILEAGE", miles);
        intent.putExtra("AVG_MILEAGE", milesAvg);
        setResult(1, intent);
        finish();
    }

    public void cancelMileage(View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        setResult(0, intent);
        finish();
    }
}