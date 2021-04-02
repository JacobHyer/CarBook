package com.Carbook.carbook;

public class MaintenanceItem {
    private String description;
    private String notes;
    private String date_maintenance;
    private int mileage;
    private int car_id;


    public MaintenanceItem(String description, String notes, int mileage, String date_maintenance, int car_id) {
        this.description = description;
        this.notes = notes;
        this.mileage = mileage;
        this.date_maintenance = date_maintenance;
        this.car_id = car_id;
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

    public int getCarId() { return car_id; }

    public void setCarId(int car_id) { this.car_id = car_id; }
}
