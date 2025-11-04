package com.example.model;

import java.time.LocalDate;

public class Booking {
    private static int idCounter = 1;

    private int id;
    private Vehicle vehicle;
    private LocalDate date;
    private String bookingType;        // "Service", "Reparation", "Besiktning"
    private double price;              // fast eller flexibel beroende på typ
    private boolean isCompleted;       // status
    private Customer customer;

    public Booking(int id, Customer customer, Vehicle vehicle, LocalDate date, String bookingType, double price, boolean isCompleted) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.date = date;
        this.bookingType = bookingType;
        this.price = price;
        this.isCompleted = isCompleted;

    }



    public int getId() { return id; }

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public Vehicle getVehicle() {return vehicle;}
    public void setVehicle(Vehicle vehicle) {this.vehicle = vehicle;}

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


    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", date=" + date +
                ", bookingType='" + bookingType + '\'' +
                ", price=" + price +
                ", isCompleted=" + isCompleted +
                '}';
    }
}