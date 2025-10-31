package com.example;

import com.example.menu.MenuHandler;
import com.example.service.BookingService;
import com.example.service.CustomerService;
import com.example.service.AnalysisService;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    private final BookingService bookingService = new BookingService();
    private final CustomerService customerService = new CustomerService();
    private final AnalysisService analysisService = new AnalysisService(bookingService, customerService);
    private final MenuHandler menuHandler = new MenuHandler(scanner, bookingService, customerService, analysisService);

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() {
        logger.info("Applikationen har startats. Väntar på användarinteraktion.");
    }

    public void run() {
        System.out.println("=== Välkommen till Bilmeckarna AB ===");
        menuHandler.runMainMenu();
        scanner.close();
        logger.info("Programmet avslutas och Scanner stängs.");
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}