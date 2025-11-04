package com.example.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private String vehicleId;
    private LocalDate date;
    private String bookingType;
    private double price;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Booking(int id, String vehicleId, LocalDate date, String bookingType, double price, String status) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.date = date;
        this.bookingType = bookingType;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", vehicleId='" + vehicleId + '\'' +
                ", date=" + date +
                ", bookingType='" + bookingType + '\'' +
                ", price=" + price +
                '}';
    }
}
