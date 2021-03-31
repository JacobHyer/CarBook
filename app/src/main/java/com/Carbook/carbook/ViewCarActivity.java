package com.Carbook.carbook;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class ViewCarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> description, notes;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Car object for testing
        Car car = new Car(null, "Nissan", "Maxima", "2012", 70000, 100, null, "Herbie");
        car.addMaintenanceItem("Oil change", "Oil change at Jiffy Lube. Recommend transmission service.", 20000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new DBHelper(this);
        description = new ArrayList<>();
        notes = new ArrayList<>();

        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, description, notes);
        recyclerView.setAdapter(maintenanceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO: Figure out how to display maintenance items
        //TODO: Copy Marcelo's storeDataInArrays method
        //TODO: Pull maintenance items from database. Jake hasn't implemented the maintenance item save pages, so may need to work around this for now....
        //TODO: Adjust MaintenanceAdapter to use MaintenanceItem objects???

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