package com.example.model;

public class Vehicle {
    private String regNum;
    private String model;
    private int year;

    // Konstruktor (för att skapa ett nytt fordon)
    public Vehicle(String regNum, String model, int year) {
        this.regNum = regNum;
        this.model = model;
        this.year = year;
    }

    // Getters (läsa värden)
    public String getRegNum() {
        return regNum;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    // 🔹 Fast pris för SERVICE beroende på årsmodell (enligt filen)
    public double getServicePrice() {
        if (year > 2020) {
            return 1500.0;
        } else if (year >= 2015) {
            return 1800.0;
        } else if (year >= 2010) {
            return 2000.0;
        } else if (year >= 2005) {
            return 2300.0;
        } else {
            return 2800.0;
        }
    }

    // Setters (ändra värden)
    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Registreringsnummer: " + regNum +
                ", Modell: " + model +
                ", Årsmodell: " + year;
    }
}
