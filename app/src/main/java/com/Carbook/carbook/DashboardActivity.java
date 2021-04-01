package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DBHelper myDB;
    ArrayList<String> id, vin, make, model, year, mileage, image;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new DBHelper(DashboardActivity.this);
        id = new ArrayList<>();
        vin = new ArrayList<>();
        make = new ArrayList<>();
        model = new ArrayList<>();
        year = new ArrayList<>();
        mileage = new ArrayList<>();
        image = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(DashboardActivity.this, id, make, model, mileage);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0 ));
                vin.add(cursor.getString(1 ));
                make.add(cursor.getString(2 ));
                model.add(cursor.getString(3 ));
                year.add(cursor.getString(4 ));
                mileage.add(cursor.getString(5 ));
                image.add(cursor.getString(6 ));
            }
        }
    }

    public void addCar (View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    public void viewCar (View view) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        startActivity(intent);
    }

    //test for MUpdateMaintenanceActivity
    public void addMaint(View view) {
        Intent intent = new Intent(this, UpdateMaintenanceActivity.class);
        startActivity(intent);
    }
}