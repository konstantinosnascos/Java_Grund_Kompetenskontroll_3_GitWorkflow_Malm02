package com.example.repository;

import com.example.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingRepository {

    public static final Logger LOG = LoggerFactory.getLogger(BookingRepository.class);
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private Map<String, LocalDateTime> timeTable = new HashMap<>();
    private  int nextId = 1;

    public BookingRepository()
    {
        LocalDateTime start = LocalDateTime.now()
                .withHour(9).withMinute(0).withSecond(0).withNano(0);
        int code = 1;

        for (int day = 0; day < 3; day++)
        {
            LocalDateTime base = start.plusDays(day);
            for (int hour = 0; hour < 5; hour++)
            {
                LocalDateTime slot = base.plusHours(hour);
                timeTable.put(String.format("T%03d", code++), slot);
            }
        }
    }

    public Map<String, LocalDateTime> getTimeTable()
    {
        return new HashMap<>(timeTable);
    }

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
            System.out.println("Inga bokningar finns.");
            return;
        }
        for(Booking booking : bookings.values()){
            System.out.println("ID: " + booking.getId() + ", Datum: " + booking.getDate() + ", Status: " + booking.isCompleted());
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
        System.out.println("Tjänst: " + booking.getServiceType());
        System.out.println("Pris: " + booking.getPrice());
        System.out.println("Anteckningar: " + booking.isCompleted());
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
//    public List<Booking> getBookingsSortedByStatus() {
//        LOG.info("Sorterar bokningar efter status (Inte klar först).");
//        return bookings.values().stream()
//                .sorted(Comparator.comparing(Booking::isCompleted)) // false först = Inte klar
//                .collect(Collectors.toList());
//    }






}