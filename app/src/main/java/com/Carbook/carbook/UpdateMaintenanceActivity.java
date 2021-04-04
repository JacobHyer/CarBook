package com.Carbook.carbook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Screen where user can add or edit service logs
 */
public class UpdateMaintenanceActivity extends AppCompatActivity {

    private DatePickerDialog picker;
    private EditText dateText;
    private EditText maintDesc;
    private EditText maintMileage;
    private EditText maintNote;
    private DBHelper db;
    private Car car;
    private int id_m;

    /**
     * Creates activity. Populates all fields from database if editing a previous service log.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        maintDesc = findViewById(R.id.maint_descrip);
        maintMileage = findViewById(R.id.maint_mileage);
        maintNote = findViewById(R.id.maint_notes);

        dateText = findViewById(R.id.editTextDate);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick (View view) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                picker = new DatePickerDialog(UpdateMaintenanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateText.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        db = new DBHelper(this);
        car = (Car)getIntent().getSerializableExtra("CAR");
        id_m = getIntent().getIntExtra("id_m", -1);
        maintMileage.setText(String.valueOf(car.getMileage()));
        if (id_m != -1) {
            MaintenanceItem mi = (MaintenanceItem)getIntent().getSerializableExtra("mi");
            maintDesc.setText(mi.getDescription());
            maintMileage.setText(String.valueOf(mi.getMileage()));
            maintNote.setText(mi.getNotes());
            dateText.setText(mi.getDateMaintenance());
        }
    }

    /**
     * Saves log entry to the local car object and in the database.
     * If mileage entered is greater than the current mileage of the car,
     * asks user if they would like to update the car's mileage
     * @param view
     */
    public void saveItem(View view) {
        String desc = maintDesc.getText().toString();
        String note = maintNote.getText().toString();
        String date = dateText.getText().toString();
        int miles = Integer.parseInt(maintMileage.getText().toString());
        MaintenanceItem mi = new MaintenanceItem(desc, note, miles, date);

        Boolean success;
        if (id_m == -1) {
            success = db.insertMaintenance(car, mi);
        } else {
            success = db.updateField("maintenance", id_m, "description", maintDesc.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "notes", maintNote.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "date_m", dateText.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "mileage", maintMileage.getText().toString());
        }

        if (success) {
            if(miles > car.getMileage()) {
                showMileageAlert(miles);
            } else {
                finish();
            }
        } else {
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT);
        }
    }

    /**
     * Returns user to the previous activity without updating item
     * @param view
     */
    public void cancelItem(View view) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("carId", car.getId());
        startActivity(intent);
    }

    /**
     * Creates alert to ask user if they would like to update their vehicle's mileage.
     * @param mileage
     */
    private void showMileageAlert(int mileage) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Update Mileage?");
        alertBuilder.setMessage("Do you want to update your vehicle's mileage to " + mileage + "?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                car.setMileage(mileage);
                db.updateField(db.CARS_TABLE, car.getId(), "mileage", String.valueOf(mileage));
                finish();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertBuilder.show();
    }
}