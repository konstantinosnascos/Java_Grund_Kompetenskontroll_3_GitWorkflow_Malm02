package com.example.model;

import java.time.LocalDate;

public class Booking {
    private static int idCounter = 1;

    private int id;
    private String vehicleReg;          // enklare att lagra än hela Vehicle-objektet
    private LocalDate date;
    private String bookingType;        // "Service", "Reparation", "Besiktning"
    private double price;              // fast eller flexibel beroende på typ
    private boolean isCompleted;       // status

    public Booking(int id, String vehicleId, LocalDate date, String bookingType, double price, boolean isCompleted) {
        this.id = id;
        this.vehicleReg = vehicleId;
        this.date = date;
        this.bookingType = bookingType;
        this.price = price;
        this.isCompleted = isCompleted;
    }

    public int getId() { return id; }

    public String getVehicleReg() { return vehicleReg; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getBookingType() { return bookingType; }
    public void setBookingType(String bookingType) { this.bookingType = bookingType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Booking.idCounter = idCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", vehicleId='" + vehicleReg + '\'' +
                ", date=" + date +
                ", bookingType='" + bookingType + '\'' +
                ", price=" + price +
                ", isCompleted=" + isCompleted +
                '}';
    }
}