package com.Carbook.carbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private EditText userVIN;
    private EditText userYear;
    private EditText userMileage;
    private ImageView carImage;
    private TextView carDescription;
    private TextView carMileage;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    public List<String> modelValues;
    private AddCarActivity activity;
    private Car newCar;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        userVIN = (EditText) findViewById(R.id.VIN_input);
        userYear = (EditText) findViewById(R.id.user_year);
        userMileage = (EditText) findViewById(R.id.user_mileage);
        carDescription = findViewById(R.id.carDescription);
        carMileage = findViewById(R.id.carMileage);
        modelValues = new ArrayList<>();
        activity = this;
        newCar = new Car(null,null,null,null,-1, null);

        DB = new DBHelper(this );

        makeSpinner = (Spinner) findViewById(R.id.make_spinner);
        ArrayAdapter<CharSequence> makeAdapter = ArrayAdapter.createFromResource(this,
                R.array.makes_spinner,
                android.R.layout.simple_spinner_item);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newCar.setMake(parent.getItemAtPosition(position).toString());
                newCar.setYear(userYear.getText().toString());
                List<String> makeParams = new ArrayList<>();
                //Params must be make first then year
                makeParams.add(newCar.getMake());
                makeParams.add(newCar.getYear());
                ModelLookupTask modelTask = new ModelLookupTask(activity, makeParams);
                Thread thread = new Thread(modelTask, "modelAPI");
                thread.start();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        userMileage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                showMileage(s.toString());
            }
        });
    }

    public void VINLookup(View view) {
        String vin = userVIN.getText().toString();
        if (vin.isEmpty()) {
            carDescription.setText("Please enter a VIN.");
            return;
        }
        newCar.setVin(vin);
        List<String> params = new ArrayList<>();
        params.add(vin);
        VinLookupTask vinTask = new VinLookupTask(this, params);
        Thread thread1 = new Thread(vinTask, "vinAPI");
        thread1.start();
    }
    public void populateModels(List<String> arr) {
        modelValues = arr;
        modelSpinner = (Spinner) findViewById(R.id.model_spinner);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                modelValues);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    newCar.setModel(parent.getItemAtPosition(position).toString());
                    showCar(newCar.getMake(), newCar.getModel(), newCar.getYear());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
    public void showMileage(String mileage) {
        newCar.setMileage(Integer.parseInt(mileage));
        carMileage.setText(newCar.getMileage() + " miles");
    }
    public void showCar(String make, String model, String year) {
        newCar.setMake(make);
        newCar.setModel(model);
        newCar.setYear(year);

        carDescription.setText(newCar.getYear() + " " + newCar.getMake() + " " + newCar.getModel());
        getImg(newCar.getMake(), newCar.getModel(), newCar.getYear());
    }
    public void getImg(String make, String model, String year) {
        ImageLookupTask imgTask = new ImageLookupTask(this, newCar);
        Thread thread2 = new Thread(imgTask, "imgAPI");
        thread2.start();
    }
    public void showImg(String url) {
        System.out.println(url);
        ImageView carImg = (ImageView) findViewById(R.id.carImage);
        if (url.equals("NotFound")) {
            Picasso.get().load(R.drawable.img_not_found).fit().into(carImg);
        } else {
            Picasso.get().load(url).fit().into(carImg);
            newCar.setImage(url);
        }
    }
    public void saveCar(View view) {
        //TODO: Add ability to send newCar to DashboardActivity (will there be a central database?)

        Boolean checkInsertData = DB.insertCar(newCar);
        if (checkInsertData != true)
            Toast.makeText(AddCarActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    public void cancelAddCar(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}