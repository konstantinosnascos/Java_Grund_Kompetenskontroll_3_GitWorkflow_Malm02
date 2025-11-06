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
                case 1 -> showBookingCountByType();
                case 2 -> running = false;
                default -> System.out.println("Felaktigt val, försök igen!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Analysmeny ---");
        System.out.println("1. Antal bokningar per typ");
        System.out.println("2. Gå tillbaka till huvudmenyn");
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

    private void showBookingCountByType() {
        System.out.println("\n--- Antal bokningar per typ ---");
        Map<String, Long> counts = analysisService.getBookingCountByType();

        if (counts.isEmpty()) {
            System.out.println("Inga bokningar än.");
        } else {
            counts.forEach((type, count) ->
                    System.out.printf("%-12s : %d bokning(ar)%n", type, count)
            );
        }
        logger.info("Analys: Antal bokningar per typ visad.");
    }
}