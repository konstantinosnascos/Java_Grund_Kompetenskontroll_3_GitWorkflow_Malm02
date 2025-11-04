package com.example.model;

public class Vehicle {
    private String regNum;
    private String model;
    private int year;

    public Vehicle(String regNum, String model, int year) {
        this.regNum = regNum;
        this.model = model;
        this.year = year;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public double calculatePrice() {
        if (year >= 2020) {
            return 1500.0;
        } else if (year >= 2015) {
            return 1200.0;
        } else if (year >= 2010) {
            return 900.0;
        } else {
            return 700.0;
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "regNum='" + regNum + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
