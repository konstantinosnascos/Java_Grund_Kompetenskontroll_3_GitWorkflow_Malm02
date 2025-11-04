package com.example.menu;

import com.example.helper.InputHelper;
import com.example.repository.BookingRepository;
import com.example.service.BookingService;
import com.example.exception.BookingConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class BookingMenu {

    private final InputHelper input;
    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingMenu.class);
    LocalDateTime startTime = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE d MMM yyyy 'kl.' HH:mm");


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
                    case 1 -> showCreateBooking();
                    case 2 -> showAllBookings();
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

    private void showCreateBooking()
    {
        System.out.println("\n--- Skapa ny bokning ---");
        int id = input.getInt("Kund-ID: ");
        String vehicleReg = input.getString("Fordonets registreringsnummer: ");
        showAvailableBookings();

        String bookTime = input.getString("Bokningstid: ");

        // Hämta vald tid från repository via koden användaren skrev
        LocalDateTime chosenTime = bookingService.bookingRepository.getTimeTable().get(bookTime);

        // Om koden inte finns
        if (chosenTime == null ) {
            System.out.println("❌ Ogiltig kod. Ingen bokning skapad.");
            return;
        }

        // Om användaren vill avbryta sin bokning
        String confirm = input.getString("Vill du skapa denna bokning [yes/no]:").trim().toLowerCase();
        if (confirm.equals("n") || confirm.equals("no"))
        {
            logger.info("Användaren valde att avbryta sin bokning");
            return;
        }

        try {
            bookingService.createBooking(id, vehicleReg, chosenTime);

            System.out.println("✅ Bokning skapad för " + chosenTime.format(FORMATTER));
            logger.info("Ny bokning skapad för kund: {} vid tid: {}", id, chosenTime.format((FORMATTER)));

        } catch (BookingConflictException e) {
            System.out.println("⚠️ Kan inte boka: " + e.getMessage());
            logger.warn("Dubbelbokning försökte skapas vid tidkod {}", bookTime);
        } catch (Exception e) {
            System.out.println("❌ Ett oväntat fel uppstod: " + e.getMessage());
            logger.error("Fel vid bokning med kund {} och fordon {}", id, vehicleReg, e);
        }
    }

    private void showAllBookings() {
        System.out.println("\n--- Alla bokningar ---");
        var bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Inga bokningar finns.");
            logger.info("Inga bokningar hittades.");
            return;
        }
        bookings.forEach(System.out::println);
    }

    private void showAvailableBookings()
    {
        System.out.println("\n---> Tillgängliga tider <---");

        var times = bookingService.getAvailableTimes();
        
        times.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry-> System.out.printf("%s → %s%n ",
                        entry.getKey(),
                        entry.getValue().format(FORMATTER)));


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