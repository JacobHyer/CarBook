package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private List<Car> carList;
    RecyclerView recyclerView;
    CardView emptyView;

    DBHelper myDB;
    CustomAdapter customAdapter;

    //TODO: If nickname is null/empty, put car description in name textView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.rvCar);
        emptyView = findViewById(R.id.empty_view);
        carList = new ArrayList<Car>();
        myDB = new DBHelper(DashboardActivity.this);

        Cursor cursor = myDB.getCars();
        if(cursor.getCount() == 0) {
            //Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                Car c = null;
                try {
                    c = myDB.createCarObject(cursor);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                carList.add(c);
            }
        }
        if (carList.size() > 0) {
            checkMileages(carList);
        }

        customAdapter = new CustomAdapter(DashboardActivity.this, carList, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));

    }
    private void checkMileages (List<Car> list) {
        Calendar today = Calendar.getInstance();
        for (Car c : list) {
            Calendar test = Calendar.getInstance();
            test.setTime(c.getMileageChanged());
            if (test != null) {
                test.add(Calendar.DATE, 30);
                if (today.compareTo(test) > 0) {
                    Intent intent = new Intent(this, MileageNotification.class);
                    intent.putExtra("CAR", c);
                    startActivity(intent);
                }
            }
        }
    }
    //test() and testIntent() are both for demonstration purposes only as it would be difficult to
    //demonstrate the notifications that are only sent 30 days after mileage is changed
    public void test(View view) {
        testIntent(carList.get(0));
    }
    public void testIntent(Car c) {
        Intent intent = new Intent(this, MileageNotification.class);
        intent.putExtra("CAR", c);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        recreate();
    }
    public void addCar (View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    public void viewCar (Car c) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("carId", c.getId());
        startActivity(intent);
    }

    public void deleteCar (int position) {
        Car c = carList.get(position);

        if(myDB.clearMaintItems(c.getMaintenanceItemList()) &&
                myDB.deleteCar(c.getId())) { // if DB delete succeeds, remove from view and show toast
            carList.remove(c);
            recyclerView.removeViewAt(position);
            customAdapter.notifyItemRemoved(position);
            customAdapter.notifyItemRangeChanged(position, carList.size());

            String name = c.getNickname();
            if(name == null || name.isEmpty()) { name = c.getFormattedDesc(); }
            Toast.makeText(this, name + " deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        viewCar(carList.get(position));
    }

    @Override
    public void onLongItemClick(int position) {
        //TODO: Set edit to long press. Make delete a button inside edit view.
        deleteCar(position);
    }
}