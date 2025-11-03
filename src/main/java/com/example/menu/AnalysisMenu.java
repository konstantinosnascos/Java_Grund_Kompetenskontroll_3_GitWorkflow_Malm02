package com.example.menu;

import com.example.helper.InputHelper;
import com.example.service.AnalysisService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisMenu {

    private final InputHelper input;
    private final AnalysisService analysisService;
    private static final Logger logger = LoggerFactory.getLogger(AnalysisMenu.class);

    public AnalysisMenu(InputHelper input, AnalysisService analysisService) {
        this.input = input;
        this.analysisService = analysisService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.getInt("Välj ett alternativ: ");
            switch (choice) {
                case 1 -> listBookingsByWeek();
                case 2 -> showTopServices();
                case 3 -> showCustomerBookingCount();
                case 4 -> running = false;
                default -> System.out.println("Felaktigt val, försök igen!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Analysmeny ---");
        System.out.println("1. Visa bokningar per vecka");
        System.out.println("2. Topp 3 service-typer");
        System.out.println("3. Antal bokningar per kund.");
        System.out.println("4. Gå tillbaka till huvudmenyn");
    }

    private void listBookingsByWeek() {
        System.out.println("\n--- Bokningar denna vecka ---");
        var bookings = analysisService.getBookingsThisWeek();
        if (bookings.isEmpty()) {
            System.out.println("Inga bokningar denna vecka.");
        } else {
            bookings.forEach(System.out::println);
        }
        logger.info("Analys: Bokningar denna vecka visade.");
    }

    private void showTopServices() {
        System.out.println("\n--- Mest bokade service-typer ---");
        Map<String, Long> topServices = analysisService.getTopServices();
        if (topServices.isEmpty()) {
            System.out.println("Inga bokningar än.");
        } else {
            topServices.forEach((type, count) ->
                    System.out.println(type + ": " + count + " st"));
        }
        logger.info("Analys: Top services visade.");
    }

    private void showCustomerBookingCount() {
        System.out.println("\n--- Antal bokningar per kund ---");
        Map<String, Long> counts = analysisService.getBookingCountPerCustomer();
        if (counts.isEmpty()) {
            System.out.println("Inga kunder har bokat än.");
        } else {
            counts.forEach((id, count) ->
                    System.out.println("Kund " + id + ": " + count + " bokningar"));
        }
        logger.info("Analys: Antal bokningar per kund visade.");
    }
}