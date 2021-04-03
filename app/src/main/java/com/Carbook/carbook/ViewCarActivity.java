package com.Carbook.carbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class ViewCarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBHelper db;
    private Car car;
    private Button btnUpdateMileage;

    public static final String TAG = "ViewCarActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Delete this section when db table is set up
        // Car object for testing
        System.out.println("View Car Activity Started");
        car = (Car)getIntent().getSerializableExtra("car");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        TextView field;
        field = findViewById(R.id.tvNickname);
        field.setText(car.getNickname());
        field = findViewById(R.id.tvCarDesc);
        field.setText(car.getFormattedDesc());
        field = findViewById(R.id.tvCarMileage);
        field.setText(car.getFormattedMileage());
        car.showImg(findViewById(R.id.ivCarImage));

        btnUpdateMileage = findViewById(R.id.btnUpdateMileage);

        recyclerView = findViewById(R.id.rvMaintenanceList);

        //clears maintenance list so only items from database will be read (avoid duplicates)
        //using Iterator to avoid ConcurrentModificationException
        for( Iterator<MaintenanceItem> iterator = car.getMaintenanceItemList().iterator(); iterator.hasNext();) {
            iterator.remove();
        }
        db = new DBHelper(this);
        Cursor cursor = db.getMaintItems((int)car.getId());
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(cursor.getColumnIndex("description")));
                car.addMaintenanceItem(
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getString(cursor.getColumnIndex("notes")),
                        cursor.getInt(cursor.getColumnIndex("mileage")),
                        cursor.getString(cursor.getColumnIndex("date_m")),
                        cursor.getInt(cursor.getColumnIndex("car_id"))
                );
            }
        }

        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, car.getMaintenanceItemList());
        recyclerView.setAdapter(maintenanceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO:
        // Pull maintenance items from database.
    }
    public void addMaintenanceItem(View view) {
        Intent intent = new Intent(this, UpdateMaintenanceActivity.class);
        intent.putExtra("CAR", car);
        startActivity(intent);
    }

    public void updateMileage(View view) {
        Intent intent = new Intent(this, MileageActivity.class);
        intent.putExtra("uniqueId", TAG);
        intent.putExtra("carId", car.getId());
        intent.putExtra(MileageActivity.EXTRA_MILEAGE, car.getMileage());
        intent.putExtra(MileageActivity.EXTRA_AVG_MILEAGE, car.getAvgMiles());
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        System.out.println(requestCode);
        System.out.println(resultCode);

        //Request code 1 is for updating mileage on car
        if (requestCode == 1 && resultCode == 1) {
            //car.setMileage(intent.getIntExtra(MileageActivity.EXTRA_MILEAGE, -1));
            //car.setAvgMiles(intent.getIntExtra(MileageActivity.EXTRA_AVG_MILEAGE, -1));
            if (car.getMileage() != -1) {
                TextView mileage = findViewById(R.id.tvCarMileage);
                mileage.setText(car.getFormattedMileage());
            }
            /*Boolean success = db.updateField("cars", car.getId(), "mileage", String.valueOf(car.getMileage()));
            if(success) success = db.updateField("cars", car.getId(), "avg_miles", String.valueOf(car.getAvgMiles()));
            if (success) {
//                intent = new Intent(this, DashboardActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "Mileage updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}