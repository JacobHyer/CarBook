package com.Carbook.carbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBHelper db;
    private Car car;
    private Button btnUpdateMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Delete this section when db table is set up
        // Car object for testing
        car = (Car)getIntent().getSerializableExtra("car");
        car.addMaintenanceItem("Oil change", "Oil change at Jiffy Lube. Recommend transmission service.", 20000);
        car.addMaintenanceItem("Tire rotation", null, 22500);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        TextView field;
        field = findViewById(R.id.tvNickname);
        field.setText(car.getNickname());
        field = findViewById(R.id.tvCarDesc);
        field.setText(car.getFormattedDesc());
        field = findViewById(R.id.tvCarMileage);
        field.setText(car.getFormattedMileage());
        //TODO: Set image

        btnUpdateMileage = findViewById(R.id.btnUpdateMileage);

        recyclerView = findViewById(R.id.rvMaintenanceList);

        db = new DBHelper(this);
        //TODO: Add cursor loop when db table is ready (see DashboardActivity)

        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, car.getMaintenanceItemList());
        recyclerView.setAdapter(maintenanceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //TODO:
        // Pull maintenance items from database.
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

    public void updateMileage(View view) {
        Intent intent = new Intent(this, MileageActivity.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //Request code 1 is for updating mileage on car
        if (requestCode == 1) {
            car.setMileage(intent.getIntExtra("MILEAGE", -1));
            car.setAvgMiles(intent.getIntExtra("AVG_MILEAGE", -1));
            if (car.getMileage() != -1) {
                TextView mileage = findViewById(R.id.tvCarMileage);
                mileage.setText(car.getFormattedMileage());
            }
            Boolean success = db.updateField("cars", car.getId(), "mileage", String.valueOf(car.getMileage()));
            if(success) success = db.updateField("cars", car.getId(), "avg_miles", String.valueOf(car.getAvgMiles()));
            if (success) {
//                intent = new Intent(this, DashboardActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "Mileage updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}