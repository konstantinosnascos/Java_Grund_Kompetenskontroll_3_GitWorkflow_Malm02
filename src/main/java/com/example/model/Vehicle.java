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

    // (Valfritt) Snygg utskrift för debug eller visning
    @Override
    public String toString() {
        return "Vehicle{" +
                "regNum='" + regNum + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
