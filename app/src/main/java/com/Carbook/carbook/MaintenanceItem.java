package com.Carbook.carbook;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MaintenanceItem implements Serializable {
    private String description;
    private String notes;
    private String date_maintenance;
    private int mileage;


    public MaintenanceItem(String description, String notes, int mileage, String date_maintenance, int car_id) {
        this.description = description;
        this.notes = notes;
        this.mileage = mileage;
        this.date_maintenance = date_maintenance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getDateMaintenance() { return date_maintenance; }

    public void setDateMaintenance(String date_maintenance) { this.date_maintenance = date_maintenance; }

    public String getFormattedMileage() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(mileage) + " mi.";
    }
}
