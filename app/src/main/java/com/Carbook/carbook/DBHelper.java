package com.Carbook.carbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Carbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        //SQL statement of the cars table creation
        DB.execSQL("CREATE TABLE cars(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " vin TEXT UNIQUE," +
                " name TEXT NOT NULL," +
                " make TEXT NOT NULL," +
                " model TEXT NOT NULL," +
                " year TEXT NOT NULL," +
                " mileage INT(9) NOT NULL," +
                " mileage_date_changed DATE NOT NULL," +
                " avg_miles INT(9)," +
                " image TEXT)");

        //SQL statement of the maintenance table creation
        DB.execSQL("CREATE TABLE maintenance(id_m INTEGER PRIMARY KEY AUTOINCREMENT," +
                " description TEXT UNIQUE," +
                " notes TEXT NOT NULL," +
                " mileage INT(9) NOT NULL," +
                " date_m DATE NOT NULL," +
                " car_id TEXT NOT NULL," +
                " FOREIGN KEY(car_id) REFERENCES cars(id))");
    }

    @Override
    public void onOpen(SQLiteDatabase DB) {
        super.onOpen(DB);
        if(!DB.isReadOnly()) {
            DB.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS cars");
        DB.execSQL("DROP TABLE IF EXISTS maintenance");
    }

    public boolean insertCar(Car car) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues car_data = new ContentValues();

        java.util.Date writeTime = new java.util.Date();

        car_data.put("vin", car.getVin());
        car_data.put("name", car.getNickname());
        car_data.put("make", car.getMake());
        car_data.put("model", car.getModel());
        car_data.put("year", car.getYear());
        car_data.put("mileage", car.getMileage());
        //car_data.put("mileage_date_changed", car.getMileageChanged());
        car_data.put("avg_miles", car.getAvgMiles());
        car_data.put("image", car.getImage());

        long id = DB.insert("cars", null, car_data);

        if(id == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean updateField(String table, long id, String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        int result = db.update(table, cv, "id =" + id, null);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertMaintenance(MaintenanceItem maintenanceItem) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues maintenance_data = new ContentValues();

        maintenance_data.put("description", maintenanceItem.getDescription());
        maintenance_data.put("notes", maintenanceItem.getNotes());
        maintenance_data.put("mileage", maintenanceItem.getMileage());
        maintenance_data.put("date_m", maintenanceItem.getDateMaintenance());
        maintenance_data.put("car_id", maintenanceItem.getCarId());

        long result=DB.insert("maintenance", null, maintenance_data);

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
