package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateMaintenanceActivity extends AppCompatActivity {

    private EditText maintDesc;
    private EditText maintMileage;
    private EditText maintNote;
    private MaintenanceItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        maintDesc = (EditText) findViewById(R.id.maint_descrip);
        maintMileage = (EditText) findViewById(R.id.maint_mileage);
        maintNote = (EditText) findViewById(R.id.maint_notes);
    }

    public void saveItem(View view) {

    }

    public void cancelItem(View view) {

    }
}