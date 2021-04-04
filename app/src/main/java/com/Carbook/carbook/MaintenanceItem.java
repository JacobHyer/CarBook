package com.Carbook.carbook;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * A maintenance task that has been performed on a Car.
 */
public class MaintenanceItem implements Serializable {
    private String description;
    private String notes;
    private String date_maintenance;
    private int mileage;
    private int id_m;


    public MaintenanceItem(String description, String notes, int mileage, String date_maintenance) {
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

    public int getId() {
        return id_m;
    }

    public void setId(int id_m) {
        this.id_m = id_m;
    }

    /**
     * Generates a formatted String for the mileage at which the maintenance was performed
     * @return the formatted String
     */
    public String getFormattedMileage() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(mileage) + " mi.";
    }

    /**
     * Generates a formatted String to display the date the maintenance was performed
     * @return the formatted String
     */
    public String getFormattedDate() {
        return "Completed: " + date_maintenance;
    }
}
