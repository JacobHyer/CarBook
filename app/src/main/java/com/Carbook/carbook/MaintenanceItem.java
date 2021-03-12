package com.Carbook.carbook;

public class MaintenanceItem {
    private String description;
    private String notes;
    private int mileage;

    public MaintenanceItem(String description, String notes, int mileage) {
        this.description = description;
        this.notes = notes;
        this.mileage = mileage;
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
}
