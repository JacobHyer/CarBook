package com.Carbook.carbook;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String make;
    private String model;
    private String year;
    private String vin;
    private int mileage;
    private List<MaintenanceItem> maintenanceItemList;

    public Vehicle(String vin, String make, String model, String year, int mileage) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.maintenanceItemList = new ArrayList<MaintenanceItem>();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void delete() {

    }

    public void addMaintenanceItem(String description, String notes, int mileage) {
        maintenanceItemList.add(new MaintenanceItem(description, notes, mileage));
    }

    public void editMaintenanceItem() {

    }

    public void deleteMaintenanceItem(MaintenanceItem maintenanceItem) {
        maintenanceItemList.remove(maintenanceItem);
    }
}
