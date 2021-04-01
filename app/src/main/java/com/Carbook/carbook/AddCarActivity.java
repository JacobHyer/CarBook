package com.Carbook.carbook;

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

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private EditText userVIN;
    private EditText userYear;
    private EditText userNickname;
    private ImageView carImage;
    private TextView carName;
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
        userNickname = (EditText) findViewById(R.id.user_nickname);
        carName = findViewById(R.id.carNickname);
        carDescription = findViewById(R.id.carDescription);
        carMileage = findViewById(R.id.tvCarMileage);
        carImage = findViewById(R.id.ivCarImage);
        modelValues = new ArrayList<>();
        activity = this;
        newCar = new Car(null,null,null,null,-1, -1, null, null);

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
                //Params must be make first then year for ModelLookupTask to work correctly
                makeParams.add(newCar.getMake());
                makeParams.add(newCar.getYear());
                ModelLookupTask modelTask = new ModelLookupTask(activity, makeParams);
                Thread thread = new Thread(modelTask, "modelAPI");
                thread.start();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        userNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                showName(s.toString());
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
    public void enterMileage(View view) {
        Intent intent = new Intent(this, MileageActivity.class);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //Request code 1 is for entering mileage on new car
        if (requestCode == 1) {
            newCar.setMileage(intent.getIntExtra("MILEAGE", -1));
            newCar.setAvgMiles(intent.getIntExtra("AVG_MILEAGE", -1));
            if (newCar.getMileage() != -1) {
                carMileage.setText(newCar.getFormattedMileage());
            }
        }
    }
    public void showName(String name) {
        newCar.setName(name);
        carName.setText(newCar.getName());
    }
    public void showCar(String make, String model, String year) {
        make = WordUtils.capitalizeFully(make);
        newCar.setMake(make);
        newCar.setModel(model);
        newCar.setYear(year);

        carDescription.setText(newCar.getFormattedDesc());
        getImg();
    }
    public void getImg() {
        ImageLookupTask imgTask = new ImageLookupTask(this, newCar);
        Thread thread2 = new Thread(imgTask, "imgAPI");
        thread2.start();
    }

    public void showImg(String url) {
        ImageView carImg = (ImageView) findViewById(R.id.ivCarImage);
        if (url.equals("NotFound")) {
            Picasso.get().load(R.drawable.img_not_found).fit().into(carImg);
        } else {
            Picasso.get().load(url).fit().into(carImg);
            newCar.setImage(url);
        }
    }

    public void saveCar(View view) {
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