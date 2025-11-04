package com.example.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private String bookingType;
    private double price;
    private String status;
    private LocalDate date;
    private Vehicle vehicle;

    public Booking(int id, String bookingType, String status, LocalDate date, Vehicle vehicle) {
        this.id = id;
        this.bookingType = bookingType;
        this.status = status;
        this.date = date;
        this.vehicle = vehicle;
        this.price = vehicle.calculatePrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.price = vehicle.calculatePrice(); // uppdatera pris om fordon ändras
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingType='" + bookingType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", vehicle=" + vehicle +
                '}';
    }
}
