package com.example.repository;

import com.example.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BookingRepository {

    public static final Logger LOG = LoggerFactory.getLogger(BookingRepository.class);
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private  int nextId = 1;


    public void addBooking(Booking booking)
    {
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
//    public void displayAllBookings(){
//        if(bookings.isEmpty()){
//            System.out.println("Inga bokningar finns.");
//            return;
//        }
//        for(Booking booking : bookings.values()){
//            System.out.println("ID: " + booking.getId() + ", Datum: " + booking.getDate() + ", Status: " + booking.getStatus());
//        }
//
//    }



}




