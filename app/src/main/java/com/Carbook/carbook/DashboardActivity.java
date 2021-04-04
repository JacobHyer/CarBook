package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Activity for the main Dashboard view of the application. Contains a list of all cars, and a button to add more.
 */

public class DashboardActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private List<Car> carList;
    RecyclerView recyclerView;
    CardView emptyView;

    DBHelper myDB;
    CarsAdapter carsAdapter;

    /**
     * Sets up the activity. Instantiates a RecyclerView for the list of cars.
     * @param savedInstanceState
     */
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

        carsAdapter = new CarsAdapter(DashboardActivity.this, carList, this);
        recyclerView.setAdapter(carsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));

    }

    /**
     * Checks when mileage of all cars was last updated, and fires an alert if it has been more than 30 days.
     * @param list List of all Cars
     */
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

    /**
     * For demonstration purposes only as it would be difficult to demonstrate the notifications
     * that are only sent 30 days after mileage is changed. Triggered by a small button bottom left of dashboard.
      * @param view
     */
    public void test(View view) {
        testIntent(carList.get(0));
    }

    /**
     * For demonstration purposes only as it would be difficult to demonstrate the notifications
     * that are only sent 30 days after mileage is changed. Triggered by a small button bottom left of dashboard.
     * @param car Car object to fire the test notification on
     */
    public void testIntent(Car car) {
        Intent intent = new Intent(this, MileageNotification.class);
        intent.putExtra("CAR", car);
        startActivityForResult(intent, 1);
    }

    /**
     * Callback function that redraws the dashboard when returning from another activity
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        recreate();
    }

    /**
     * Launches AddCarActivity when add button is clicked
     */
    public void addCar(View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    /**
     * Launches ViewCarActivity when a car card is clicked
     * @param car the Car
     */
    public void viewCar (Car car) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("carId", car.getId());
        startActivity(intent);
    }

    /**
     * Deletes a car from the database and removes it from the view
     * @param position the position of the recyclerview the selected car is at
     */
    public void deleteCar (int position) {
        Car c = carList.get(position);

        if(myDB.clearMaintItems(c.getMaintenanceItemList()) &&
                myDB.deleteCar(c.getId())) { // if DB delete succeeds, remove from view and show toast
            carList.remove(c);
            recyclerView.removeViewAt(position);
            carsAdapter.notifyItemRemoved(position);
            carsAdapter.notifyItemRangeChanged(position, carList.size());

            String name = c.getNickname();
            if(name == null || name.isEmpty()) { name = c.getFormattedDesc(); }
            Toast.makeText(this, name + " deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Custom click listener for a car card. Launches viewCar
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        viewCar(carList.get(position));
    }

    /**
     * Custom long click listener for a car card. Deletes the car.
     * @param position
     */
    @Override
    public void onLongItemClick(int position) {
        //TODO: Set edit to long press. Make delete a button inside edit view.
        deleteCar(position);
    }
}