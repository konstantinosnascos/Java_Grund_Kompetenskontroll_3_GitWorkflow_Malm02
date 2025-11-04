package com.example.model;

import java.time.LocalDate;

public class Booking {
    private static int idCounter = 1;

    private int bookingID;
    private Vehicle vehicle;
    private LocalDate bookingDate;
    private boolean isCompleted; // false = inte klar, true = klar
    private String bookingType; // "Service", "Reparation", "Besiktning"
    private double repairPrice; // används endast för reparation

    public Booking(Vehicle vehicle, LocalDate bookingDate, String bookingType) {
        this.bookingID = idCounter++; // autoincrement
        this.vehicle = vehicle;
        this.bookingDate = bookingDate;
        this.bookingType = bookingType;
        this.isCompleted = false; // standard: inte klar
        this.repairPrice = 0.0;
    }

    public int getBookingID() {
        return bookingID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public void setRepairPrice(double repairPrice) {
        this.repairPrice = repairPrice;
    }

    // 🔹 Pris baserat på typ
    public double getPrice() {
        switch (bookingType.toLowerCase()) {
            case "service":
                return vehicle.getServicePrice(); // fast pris per årsmodell
            case "besiktning":
                return 550.0; // fast pris
            case "reparation":
                return repairPrice; // flexibelt pris (sätts externt)
            default:
                return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Boknings-ID: " + bookingID +
                ", Typ: " + bookingType +
                ", Datum: " + bookingDate +
                ", Fordon: [" + vehicle + "]" +
                ", Status: " + (isCompleted ? "Klar" : "Inte klar") +
                ", Pris: " + getPrice() + " kr";
    }
}
