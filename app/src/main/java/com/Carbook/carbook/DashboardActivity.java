package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private List<Car> carList;
    RecyclerView recyclerView;
    DBHelper myDB;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.rvCar);
        carList = new ArrayList<Car>();
        myDB = new DBHelper(DashboardActivity.this);

        Cursor cursor = myDB.getCars();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                carList.add(new Car(
                        cursor.getString(cursor.getColumnIndex("vin")),
                        cursor.getString(cursor.getColumnIndex("make")),
                        cursor.getString(cursor.getColumnIndex("model")),
                        cursor.getString(cursor.getColumnIndex("year")),
                        cursor.getInt(cursor.getColumnIndex("mileage")),
                        cursor.getInt(cursor.getColumnIndex("avg_miles")),
                        cursor.getString(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("name"))
                ));
            }
        }
        //checkMileages(carList);
        customAdapter = new CustomAdapter(DashboardActivity.this, carList, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
    }
    private void checkMileages (List<Car> list) {
        Calendar today = Calendar.getInstance();
        for (Car c : list) {
            Calendar test = c.getMileageChanged();
            test.add(Calendar.DATE, 30);
            if (today.compareTo(test) > 0) {
                Intent intent = new Intent(this, MileageNotification.class);
                intent.putExtra("MILEAGE", c.getMileage());
                intent.putExtra("AVG_MILES", c.getAvgMiles());
                startActivityForResult(intent, 1);
            }
        }
    }

    public void test(View view) {
        testIntent(carList.get(0));
    }
    public void testIntent(Car c) {
        Intent intent = new Intent(this, MileageNotification.class);
        intent.putExtra("CAR", c);
        startActivity(intent);
    }
    public void addCar (View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    public void viewCar (Car c) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("car", c);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        viewCar(carList.get(position));
    }

    @Override
    public void onLongItemClick(int position) {

        //TODO: Add edit (or delete?) on long press
    }
}