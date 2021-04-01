package com.Carbook.carbook;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Car implements Serializable {
    @SerializedName("Make")
    private String make;
    @SerializedName("Model")
    private String model;
    @SerializedName("ModelYear")
    private String year;
    private String vin;
    private int mileage;
    private Calendar dateChanged;
    private int avg_miles;
    private String image;
    private String name;
    private List<MaintenanceItem> maintenanceItemList;

    public Car(String vin, String make, String model, String year, int mileage, int avg_miles, String image, String name) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.dateChanged = null;
        this.avg_miles = avg_miles;
        this.image = image;
        this.name = name;
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

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Calendar getMileageChanged() {
        return dateChanged;
    }

    public void setMileageChanged(Calendar date) {
        this.dateChanged = date;
    }

    public int getAvgMiles() {
        return avg_miles;
    }

    public void setAvgMiles(int avg_miles) {
        this.avg_miles = avg_miles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void delete() {

    }

    public void addMaintenanceItem(String description, String notes, int mileage) {
        maintenanceItemList.add(new MaintenanceItem(description, notes, mileage));
    }

    public List<MaintenanceItem> getMaintenanceItemList() {
        return maintenanceItemList;
    }

    public void editMaintenanceItem() {

    }

    public void deleteMaintenanceItem(MaintenanceItem maintenanceItem) {
        maintenanceItemList.remove(maintenanceItem);
    }

    public String getFormattedDesc() {
        return year + " " + make + " " + model;
    }

    public String getFormattedMileage() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(mileage) + " mi.";
    }
}
