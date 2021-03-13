package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private EditText userVIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        userVIN = (EditText) findViewById(R.id.VIN_input);
    }

    public void VINLookup(View view) {
        String vin = userVIN.getText().toString();
        List<String> params = new ArrayList<>();
        params.add(vin);
        VINLookupTask task = new VINLookupTask(this, "vin", params);
        task.execute();
    }

    public void cancelAddVehicle(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
