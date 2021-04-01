package com.Carbook.carbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Carbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE cars(id INTEGER PRIMARY KEY AUTOINCREMENT, vin TEXT UNIQUE, make TEXT, model TEXT, year TEXT, mileage INT(9), image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS cars");
    }

    public boolean insertCar(Car car) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues car_data = new ContentValues();

        car_data.put("vin", car.getVin());
        car_data.put("make", car.getMake());
        car_data.put("model", car.getModel());
        car_data.put("year", car.getYear());
        car_data.put("mileage", car.getMileage());
        car_data.put("image", car.getImage());

        long result=DB.insert("cars", null, car_data);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getCars() {
        String query = "SELECT * FROM cars";
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null) {
            cursor = DB.rawQuery(query, null);
        }
        return cursor;
    }
}
