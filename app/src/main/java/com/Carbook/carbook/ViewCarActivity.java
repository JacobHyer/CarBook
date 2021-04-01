package com.Carbook.carbook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewCarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> description, notes;
    List<Integer> mileage;
    DBHelper myDB;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Car object for testing
        car = (Car)getIntent().getSerializableExtra("car");
//        car = new Car(null, "Nissan", "Maxima", "2012", 70000, 100, null, "Herbie");
        car.addMaintenanceItem("Oil change", "Oil change at Jiffy Lube. Recommend transmission service.", 20000);
        car.addMaintenanceItem("Tire rotation", null, 22500);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        recyclerView = findViewById(R.id.rvMaintenanceList);

        TextView field;
        field = findViewById(R.id.tvCarDesc);
        field.setText(car.getFormattedDesc());
        field = findViewById(R.id.tvCarMileage);
        field.setText(car.getFormattedMileage());
        //TODO: Set image

        myDB = new DBHelper(this);
        description = new ArrayList<>();
        notes = new ArrayList<>();
        mileage = new ArrayList<>();

        for (MaintenanceItem mi : car.getMaintenanceItemList()) {
            description.add(mi.getDescription());
            notes.add(mi.getNotes());
            mileage.add(mi.getMileage());
        }

        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, description, notes, mileage);
        recyclerView.setAdapter(maintenanceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO:
        // Pull maintenance items from database.
        // Adjust MaintenanceAdapter to use MaintenanceItem objects???
        // Not sure what the below commented code is for. Git says I (Ryan) added it, but I don't remember doing that. Definitely possible I copy/pasted it from somewhere while trying to figure something out.

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}