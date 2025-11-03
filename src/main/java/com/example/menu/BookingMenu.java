package com.example.menu;

import com.example.helper.InputHelper;
import com.example.service.BookingService;
import com.example.exception.BookingConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

import java.time.LocalDateTime;

public class BookingMenu {

    private final InputHelper input;
    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingMenu.class);

    public BookingMenu(InputHelper input, BookingService bookingService) {
        this.input = input;
        this.bookingService = bookingService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = input.getInt("Välj ett alternativ: ");
                switch (choice) {
                    case 1 -> createBooking();
                    case 2 -> listAllBookings();
                    case 3 -> cancelBooking();
                    case 4 -> running = false;
                    default -> System.out.println("Felaktigt val, försök igen!");
                }
            } catch (Exception e) {
                System.out.println("Ett oväntat fel uppstod i bokningsmenyn: " + e.getMessage());
                logger.error("Fel i BookingMenu.run()", e);
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Bokningsmeny ---");
        System.out.println("1. Skapa ny bokning");
        System.out.println("2. Visa alla bokningar");
        System.out.println("3. Avboka");
        System.out.println("4. Gå tillbaka till huvudmenyn");
    }

    private void createBooking() {
        System.out.println("\n--- Skapa ny bokning ---");
        String customerId = input.getString("Kund-ID: ");
        String vehicleReg = input.getString("Fordonets registreringsnummer: ");
        LocalDateTime time = input.getDateTime("Bokningstid");

        try {
            bookingService.createBooking(customerId, vehicleReg, time);
            System.out.println("Bokning skapad för " + time);
            logger.info("Ny bokning skapad för kund: {} vid tid: {}", customerId, time);
        } catch (BookingConflictException e) {
            System.out.println("Kan inte boka: " + e.getMessage());
            logger.warn("Dubbelbokning försökte skapas vid tid: {}", time);
        } catch (Exception e) {
            System.out.println("Ett oväntat fel uppstod vid bokning: " + e.getMessage());
            logger.error("Fel vid bokning med kund {} och fordon {}", customerId, vehicleReg, e);
        }
    }

    private void listAllBookings() {
        System.out.println("\n--- Alla bokningar ---");
        var bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Inga bokningar finns.");
            logger.info("Inga bokningar hittades.");
            return;
        }
        bookings.forEach(System.out::println);
    }

    private void cancelBooking() {
        System.out.println("\n--- Avboka ---");
        String bookingId = input.getString("Ange boknings-ID för att avboka: ");
        boolean removed = bookingService.cancelBooking(bookingId);

        if (removed) {
            System.out.println("Bokning avbokad.");
            logger.info("Bokning med ID {} avbokad.", bookingId);
        } else {
            System.out.println("Bokning hittades inte.");
            logger.warn("Försökte avboka ID {} men hittades inte.", bookingId);
        }
    }

//    private LocalDateTime chooseBookingTime()
//    {
//        System.out.println("---> Tillgängliga tider <---");
//
//        for(int i = 0; i < 5; i++)
//        {
//            times.add(startTime.plusHours(i));
//        }
//
//
//    }
}