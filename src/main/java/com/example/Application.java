package com.example;

import com.example.menu.MenuHandler;
import com.example.service.BookingService;
import com.example.service.CustomerService;
import com.example.service.AnalysisService;
import com.example.service.EmailService;

import com.example.model.Customer;
import com.example.model.Vehicle;
import com.example.model.ServiceType;

import java.time.LocalDateTime;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    private final BookingService bookingService = new BookingService();
    private final CustomerService customerService = new CustomerService();
    private final EmailService emailService = new EmailService();
    private final AnalysisService analysisService = new AnalysisService(bookingService, customerService);
    private final MenuHandler menuHandler = new MenuHandler(scanner, bookingService, customerService, analysisService, emailService);

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() {
        logger.info("Applikationen har startats. Lägger till data. Väntar på användarinteraktion.");

        initializeTestData();
    }

    private void initializeTestData()
    {
        try {
            Customer customer1 = new Customer();
            customer1.setName("Anna Andersson");
            customer1.setEmail("anna@email.com");

            Vehicle vehicle1 = new Vehicle("ABC321", "Volvo V70", 2018);
            LocalDateTime bookingTime1 = LocalDateTime.now()
                    .plusDays(3)
                    .plusHours(15);

            //  nytt: skickar med null för åtgärd (inte reparation)
            bookingService.createBooking(customer1, vehicle1, bookingTime1, ServiceType.SERVICE, "T001", null);


            Customer customer2 = new Customer();
            customer2.setName("Erik Svensson");
            customer2.setEmail("erik.svensson@email.com");

            Vehicle vehicle2 = new Vehicle("XYZ98B", "Toyota Corolla", 2015);

            LocalDateTime bookingTime2 = LocalDateTime.now()
                    .plusDays(1)
                    .withHour(14);

            //  nytt: skickar med en beskrivning eftersom det är reparation
            bookingService.createBooking(customer2, vehicle2, bookingTime2, ServiceType.REPARATION, "T005","Bromsbyte fram");


            Customer customer3 = new Customer();
            customer3.setName("Karl Carlsson");
            customer3.setEmail("kalle@email.com");

            Vehicle vehicle3 = new Vehicle("KFN123", "Renault Captur", 2016);
            LocalDateTime bookingTime3 = LocalDateTime.now()
                    .plusDays(9)
                    .plusHours(15);

            //  nytt: null eftersom det inte är reparation
            bookingService.createBooking(customer3, vehicle3, bookingTime3, ServiceType.BESIKTNING, "T008", null);

            logger.info("Testdata: 3 bokningar tillagda.");
        } catch (Exception e)
        {
            System.out.println("Det gick lite fel där tror jag." + e.getMessage());
            logger.error("Fel vid initiering av data.", e);
        }
    }

    public void run() {
        System.out.println("=== Välkommen till Bilmeckarna AB! ===");
        menuHandler.runMainMenu();
        scanner.close();
        logger.info("Programmet avslutas och Scanner stängs.");
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
