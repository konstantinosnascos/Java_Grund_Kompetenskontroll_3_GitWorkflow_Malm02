package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDateTime date;
    private ServiceType serviceType;
    private double price;
    private boolean completed;
    private String action; // 👈 nytt attribut för åtgärd (endast vid reparation)

    // Konstruktor utan ID (för ny bokning)
    public Booking(Customer customer, Vehicle vehicle, LocalDateTime date, ServiceType serviceType, double price, boolean completed, String action) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.date = date;
        this.serviceType = serviceType;
        this.price = price;
        this.completed = completed;
        this.action = action;
    }

    // Konstruktor med ID (för befintlig bokning)
    public Booking(int id, Customer customer, Vehicle vehicle, LocalDateTime date, ServiceType serviceType, double price, boolean completed, String action) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.date = date;
        this.serviceType = serviceType;
        this.price = price;
        this.completed = completed;
        this.action = action;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    // Metod för att skriva ut all info
    public void printInfo(DateTimeFormatter formatter) {
        System.out.println("\n--- Bokningsinformation ---");
        System.out.println("ID: " + id);
        System.out.println("Kund: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("Fordon: " + vehicle.getRegNum() + " - " + vehicle.getModel() + " (" + vehicle.getYear() + ")");
        System.out.println("Typ av tjänst: " + serviceType);
        if (serviceType == ServiceType.REPARATION && action != null && !action.isBlank()) {
            System.out.println("Åtgärd: " + action);
        }
        System.out.println("Datum & tid: " + date.format(formatter));
        System.out.println("Pris: " + price + " kr");
        System.out.println("Status: " + (completed ? "✅ Klar" : "⏳ Inte klar"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bokning [ID=").append(id)
                .append(", Kund=").append(customer.getName())
                .append(", Fordon=").append(vehicle.getRegNum())
                .append(", Typ=").append(serviceType);

        if (serviceType == ServiceType.REPARATION && action != null && !action.isBlank()) {
            sb.append(", Åtgärd=").append(action);
        }

        sb.append(", Datum=").append(date)
                .append(", Pris=").append(price)
                .append(", Klar=").append(completed)
                .append("]");
        return sb.toString();
    }
}
