package com.Carbook.carbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Iterator;

public class ViewCarActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private RecyclerView recyclerView;
    private DBHelper db;
    private Car car;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvCarMileage;
    private ImageView ivCarImage;
    public static final String TAG = "ViewCarActivity";
    private MaintenanceAdapter maintenanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        db = new DBHelper(this);
        Long carId = getIntent().getLongExtra("carId", -1);
        Cursor carCursor = db.getOneCar(carId);
        if (carCursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (carCursor.moveToNext()) {
                try {
                    car = db.createCarObject(carCursor);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        tvTitle =  findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvCarMileage = findViewById(R.id.tvCarMileage);
        ivCarImage = findViewById(R.id.ivCarImage);
        CarbookUtil.buildCardView(car, tvTitle, tvSubtitle, tvCarMileage, ivCarImage);

        recyclerView = findViewById(R.id.rvMaintenanceList);

        //clears maintenance list so only items from database will be read (avoid duplicates)
        //using Iterator to avoid ConcurrentModificationException
        Iterator<MaintenanceItem> iterator = car.getMaintenanceItemList().iterator();
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        Cursor cursor = db.getMaintItems((int)car.getId());
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                MaintenanceItem mi = db.createMaintObject(cursor);
                car.addMaintenanceItem(mi);
            }
        }

        maintenanceAdapter = new MaintenanceAdapter(this, car.getMaintenanceItemList(), this);
        recyclerView.setAdapter(maintenanceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addMaintenanceItem(View view) {
        Intent intent = new Intent(this, UpdateMaintenanceActivity.class);
        intent.putExtra("CAR", car);
        intent.putExtra("id_m", -1);
        startActivityForResult(intent, 3);
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
        if(requestCode == 3) {
            recreate();
            Toast.makeText(this, "Service event saved", Toast.LENGTH_SHORT);
        }
    }

    public void viewItem(MaintenanceItem mi) {
        Intent intent = new Intent(this, UpdateMaintenanceActivity.class);
        intent.putExtra("CAR", car);
        intent.putExtra("id_m", mi.getId());
        intent.putExtra("mi", mi);
        startActivityForResult(intent, 4);
    }

    public void deleteItem(int position) {
        MaintenanceItem mi = car.getMaintenanceItemList().get(position);

        if(db.deleteMaintenance(mi.getId())) { //position is -1 from rowId
            car.deleteMaintenanceItem(mi);
            recyclerView.removeViewAt(position);
            maintenanceAdapter.notifyItemRemoved(position);
            maintenanceAdapter.notifyItemRangeChanged(position, car.getMaintenanceItemList().size());

            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        viewItem(car.getMaintenanceItemList().get(position));
    }

    @Override
    public void onLongItemClick(int position) {
        deleteItem(position);
    }
}