package com.Carbook.carbook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateMaintenanceActivity extends AppCompatActivity {

    private DatePickerDialog picker;
    private EditText dateText;
    private EditText maintDesc;
    private EditText maintMileage;
    private EditText maintNote;
    private MaintenanceItem item;
    private DBHelper db;
    private Car car;
    private int id_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        maintDesc = (EditText) findViewById(R.id.maint_descrip);
        maintMileage = (EditText) findViewById(R.id.maint_mileage);
        maintNote = (EditText) findViewById(R.id.maint_notes);

        dateText = (EditText) findViewById(R.id.editTextDate);
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
        System.out.println(id_m);
        if (id_m != -1) {
            MaintenanceItem mi = (MaintenanceItem)getIntent().getSerializableExtra("mi");
            maintDesc.setText(mi.getDescription());
            maintMileage.setText(String.valueOf(mi.getMileage()));
            maintNote.setText(mi.getNotes());
            dateText.setText(mi.getDateMaintenance());
        }
        item = new MaintenanceItem(null, null,-1,null,(int)(car.getId()));
    }

    public void saveItem(View view) {
        Boolean success = null;
        if (id_m == -1) {
            success = db.insertMaintenance(car, item);
        } else {
            success = db.updateField("maintenance", id_m, "description", maintDesc.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "notes", maintNote.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "date_m", dateText.getText().toString());
            if(success) success = db.updateField("maintenance", id_m, "mileage", maintMileage.getText().toString());
        }
        if (success) {
            Intent intent = new Intent(this, ViewCarActivity.class);
            intent.putExtra("carId", car.getId());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT);
        }
    }

    public void cancelItem(View view) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("carId", car.getId());
        startActivity(intent);
    }
}