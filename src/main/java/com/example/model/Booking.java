package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private static int idCounter = 1;

    private int id;
    private Vehicle vehicle;
    private LocalDateTime date;
    private ServiceType serviceType;        // "Service", "Reparation", "Besiktning"
    private double price;              // fast eller flexibel beroende på typ
    private boolean isCompleted;       // status
    private Customer customer;

    public Booking(int id, Customer customer, Vehicle vehicle, LocalDateTime date, ServiceType serviceType, double price, boolean isCompleted) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.date = date;
        this.serviceType = serviceType;
        this.price = price;
        this.isCompleted = isCompleted;

    }



    public int getId() { return id; }

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public Vehicle getVehicle() {return vehicle;}
    public void setVehicle(Vehicle vehicle) {this.vehicle = vehicle;}

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public ServiceType getServiceType() {return serviceType ;}
    public void setServiceType(ServiceType serviceType) {this.serviceType = serviceType; }

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
                ", bookingType='" + serviceType + '\'' +
                ", price=" + price +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public void printInfo(DateTimeFormatter formatter)
    {
        System.out.println("--------------------------------------");
        System.out.printf("Boknings-ID:  %d%n", id);
        System.out.printf("Kund:         %s (%s)%n", customer.getName(), customer.getEmail());
        System.out.printf("Fordon:       %s (%s, %d)%n", vehicle.getModel(), vehicle.getRegNum(), vehicle.getYear());
        System.out.printf("Datum:        %s%n", date.format(formatter));
        System.out.printf("Typ:          %s%n", serviceType);
        System.out.printf("Pris:         %.2f kr%n", price);
        System.out.printf("Status:       %s%n", isCompleted ? "Klar" : "Bokad");
        System.out.println("--------------------------------------");
    }
}