package com.example.repository;

import com.example.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingRepository {

    public static final Logger LOG = LoggerFactory.getLogger(BookingRepository.class);
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private  int nextId = 1;

    //f1-add bokning
    public void addBooking(Booking booking) {
        int id = nextId++;
        booking.setId(id);
        bookings.put(id, booking);
    }

    //f2- redigera  bokning
    public boolean editBooking(int id, Booking updatedBooking) {
        if (!bookings.containsKey(id)) {
            LOG.warn("Bokning med ID {} hittades inte för redigering.", id);
            return false;
        }
        updatedBooking.setId(id);
        bookings.put(id, updatedBooking);
        LOG.info("Bokning med ID {} uppdaterad.", id);
        return true;
    }

    //f3- ta bort bokning
    public boolean deleteBooking(int id) {
        if (bookings.containsKey(id)) {
            bookings.remove(id);
            LOG.info("Bokning med ID {} borttagen.", id);
            return true;
        }
        LOG.warn("Ingen bokning med ID {} hittades för borttagning.", id);
        return false;
    }

    //f4-visa alla bokningar
    public void displayAllBookings(){
        if(bookings.isEmpty()){
            LOG.info("Inga bokningar att visa.");
            return;
        }
        for(Booking booking : bookings.values()){
            LOG.info("ID: " + booking.getId() + ", Datum: " + booking.getDate() + ", price: " + booking.getPrice());
        }

    }

    // F5 – Visa bokningsdetaljer via ID
    public void displayBookingDetails(int id) {
        Booking booking = bookings.get(id);
        if (booking == null) {
            LOG.warn("Ingen bokning med ID {} hittades.", id);
            return;
        }

        LOG.info("Visar detaljer för bokning med ID {}.", id);
        System.out.println("=== Bokningsdetaljer ===");
        System.out.println("ID: " + booking.getId());
        System.out.println("Datum: " + booking.getDate());
        //System.out.println("Kund: " + booking.getCustomerName());
        System.out.println("Tjänst: " + booking.getBookingType());
        System.out.println("Pris: " + booking.getPrice());
        System.out.println("Anteckningar: " + booking.getStatus());
    }

    //f-13 Sortera efter ID (stigande)
    public List<Booking> getBookingsSortedById() {
        LOG.info("Sorterar bokningar efter ID.");
        return bookings.values().stream()
                .sorted(Comparator.comparingInt(Booking::getId))
                .collect(Collectors.toList());
    }

    //f-13 Sortera efter datum (tidigast först)
    public List<Booking> getBookingsSortedByDate() {
        LOG.info("Sorterar bokningar efter datum.");
        return bookings.values().stream()
                .sorted(Comparator.comparing(Booking::getDate))
                .collect(Collectors.toList());
    }

    //f-13 Sortera efter status (Inte klar först)
    public List<Booking> getBookingsSortedByStatus() {
        LOG.info("Sorterar bokningar efter status (Inte klar först).");
        return bookings.values().stream()
                .sorted(Comparator.comparing(Booking::isCompleted)) // false först = Inte klar
                .collect(Collectors.toList());
    }






}