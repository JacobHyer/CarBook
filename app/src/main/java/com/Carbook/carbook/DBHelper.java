package com.Carbook.carbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public final String CARS_TABLE = "cars";
    public final String MAINTENANCE_ITEM_TABLE = "maintenance";

    public DBHelper(Context context) {
        super(context, "Carbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        //SQL statement of the cars table creation
        DB.execSQL("CREATE TABLE cars(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " vin TEXT UNIQUE," +
                " name TEXT," +
                " make TEXT NOT NULL," +
                " model TEXT NOT NULL," +
                " year TEXT NOT NULL," +
                " mileage INT(9) NOT NULL," +
                " mileage_date_changed DATE NOT NULL," +
                " avg_miles INT(9)," +
                " image TEXT)");

        //SQL statement of the maintenance table creation
        DB.execSQL("CREATE TABLE maintenance(id_m INTEGER PRIMARY KEY AUTOINCREMENT," +
                " description TEXT NOT NULL," +
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

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean insertCar(Car car) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues car_data = new ContentValues();

        car_data.put("vin", car.getVin());
        car_data.put("name", car.getNickname());
        car_data.put("make", car.getMake());
        car_data.put("model", car.getModel());
        car_data.put("year", car.getYear());
        car_data.put("mileage", car.getMileage());
        car_data.put("mileage_date_changed", getDateTime());
        car_data.put("avg_miles", car.getAvgMiles());
        car_data.put("image", car.getImage());

        long id = DB.insert(CARS_TABLE, null, car_data);

        if(id == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleteCar(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CARS_TABLE, "id=" + id, null);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean clearMaintItems(List<MaintenanceItem> itemList) {
        for (MaintenanceItem mi : itemList) {
            if (deleteMaintenance(mi.getId())) {
            } else {
                return false;
            }
        }
        return true;
    }

    public Car createCarObject(Cursor cursor) {
        Car c = new Car(
                cursor.getString(cursor.getColumnIndex("vin")),
                cursor.getString(cursor.getColumnIndex("make")),
                cursor.getString(cursor.getColumnIndex("model")),
                cursor.getString(cursor.getColumnIndex("year")),
                cursor.getInt(cursor.getColumnIndex("mileage")),
                cursor.getInt(cursor.getColumnIndex("avg_miles")),
                cursor.getString(cursor.getColumnIndex("image")),
                cursor.getString(cursor.getColumnIndex("name"))
        );
        //save db id to Car object for easier reference to db later
        c.setId(cursor.getLong(cursor.getColumnIndex("id")));
        //Add all saved MIs to car object (for clean deletion in Dashboard Activity)
        Cursor miCursor = getMaintItems((int)c.getId());
        if (cursor.getCount() == 0) {
        } else {
            while (miCursor.moveToNext()) {
                MaintenanceItem mi = createMaintObject(miCursor);
                c.addMaintenanceItem(mi);
            }
        }
        return c;
    }

    public MaintenanceItem createMaintObject(Cursor cursor) {
        MaintenanceItem mi = new MaintenanceItem(
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getString(cursor.getColumnIndex("notes")),
                cursor.getInt(cursor.getColumnIndex("mileage")),
                cursor.getString(cursor.getColumnIndex("date_m")),
                cursor.getInt(cursor.getColumnIndex("car_id"))
        );
        //save db id to MaintenanceItem object for easier reference to db later
        mi.setId(cursor.getInt(cursor.getColumnIndex("id_m")));
        return mi;
    }

    public boolean updateField(String table, long id, String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        int result = -1;
        if (table == "cars") {
            result = db.update(table, cv, "id =" + id, null);
        } else if (table == "maintenance") {
            result = db.update(table, cv, "id_m =" + id, null);
        }

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertMaintenance(Car car, MaintenanceItem maintenanceItem) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues maintenance_data = new ContentValues();

        maintenance_data.put("description", maintenanceItem.getDescription());
        maintenance_data.put("notes", maintenanceItem.getNotes());
        maintenance_data.put("mileage", maintenanceItem.getMileage());
        maintenance_data.put("date_m", maintenanceItem.getDateMaintenance());
        maintenance_data.put("car_id", car.getId());

        long result=DB.insert(MAINTENANCE_ITEM_TABLE, null, maintenance_data);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteMaintenance(int id_m) {
        SQLiteDatabase db = this.getReadableDatabase();
        int result = db.delete(MAINTENANCE_ITEM_TABLE, "id_m=" + id_m, null);

        if (result == -1) {
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

    public Cursor getOneCar(long carId) {
        String query = "SELECT * FROM cars WHERE id = " + carId;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if (DB != null) {
            cursor = DB.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getMaintItems(int carId) {
        String query = "SELECT * FROM maintenance WHERE car_id = " + carId;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if (DB != null) {
            cursor = DB.rawQuery(query, null);
        }
        return cursor;
    }
}
