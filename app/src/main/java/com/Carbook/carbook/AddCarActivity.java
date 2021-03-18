package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private EditText userVIN;
    private EditText vehicleDetails;
    private Car newCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        userVIN = (EditText) findViewById(R.id.VIN_input);
        vehicleDetails = (EditText) findViewById(R.id.vehicleDetails);
        newCar = new Car(null,null,null,null,-1);
    }

    public void VINLookup(View view) {
        String vin = userVIN.getText().toString();
        if (vin.isEmpty()) {
            vehicleDetails.setText("Please enter a VIN.");
            return;
        }
        List<String> params = new ArrayList<>();
        params.add(vin);
        VINLookupTask task = new VINLookupTask(this, "vin", params);
        Thread thread1 = new Thread(task, "vinAPI");
        thread1.start();
    }
    public void showCar(String make, String model, String year) {
        newCar.setMake(make);
        newCar.setModel(model);
        newCar.setYear(year);

        vehicleDetails.setText("Make: " + newCar.getMake());
        vehicleDetails.append("\nModel: " + newCar.getModel());
        vehicleDetails.append("\nYear: " + newCar.getYear());
    }
    public void saveCar(View view) {
        //TODO: Add ability to send newCar to DashboardActivity (will there be a central database?)
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    public void cancelAddCar(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
