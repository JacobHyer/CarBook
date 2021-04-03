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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        maintDesc = (EditText) findViewById(R.id.maint_descrip);
        maintMileage = (EditText) findViewById(R.id.maint_mileage);
        maintNote = (EditText) findViewById(R.id.maint_notes);
        item = new MaintenanceItem(null, null,-1,null,-1);
        db = new DBHelper(this);

        car = (Car)getIntent().getSerializableExtra("CAR");

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
    }

    public void saveItem(View view) {
        //TODO: Save maintenance item to the database/car object
        /*item.setDescription(maintDesc.getText().toString());
        item.setNotes(maintNote.getText().toString());
        item.setMileage(Integer.parseInt(maintMileage.getText().toString()));
        //TODO: Calendars for maint and see if IDs can be put as same type
        item.setDateMaintenance("test");
        item.setCarId((int)(car.getId()));
        System.out.println(item.getNotes());
        System.out.println(item.getMileage());
        System.out.println(item.getDateMaintenance());
        System.out.println(item.getCarId());
        Boolean success = db.insertMaintenance(item);
        if (success) {
            Intent intent = new Intent(this, ViewCarActivity.class);
            setResult(2, intent);
            finish();
        }*/
    }

    public void cancelItem(View view) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        intent.putExtra("CAR", car);
        setResult(0, intent);
        finish();
    }
}