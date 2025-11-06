package com.example.menu;

import com.example.helper.InputHelper;
import com.example.service.AnalysisService;
import com.example.service.BookingService;
import com.example.service.CustomerService;
import com.example.service.EmailService;

import java.util.Scanner;

public class MenuHandler {

    private final InputHelper input;
    private final BookingMenu bookingMenu;
    private final AnalysisMenu analysisMenu;

    public MenuHandler(Scanner scanner,
                       BookingService bookingService,
                       CustomerService customerService,
                       AnalysisService analysisService,
                       EmailService emailService)
    {
        this.input = new InputHelper(scanner);
        this.bookingMenu = new BookingMenu(input, bookingService, emailService);
        this.analysisMenu = new AnalysisMenu(input, analysisService);

    }

    public void runMainMenu()
    {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = input.getInt("Välj alternativ: ");
            switch (choice) {
                case 1 -> bookingMenu.run();
                case 2 -> analysisMenu.run();
                case 3 -> running = false;
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }
    private void printMainMenu() {
        System.out.println("\n--- Bilmeckarna AB ---");
        System.out.println("1. Hantera bokningar");
        System.out.println("2. Visa analyser");
        System.out.println("3. Avsluta");
    }
}
