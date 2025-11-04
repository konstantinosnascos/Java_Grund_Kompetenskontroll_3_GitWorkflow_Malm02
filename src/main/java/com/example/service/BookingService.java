package com.example.service;

import com.example.model.Booking;
import com.example.model.Customer;
import com.example.model.Vehicle;
import com.example.repository.BookingRepository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookingService {

    public final BookingRepository bookingRepository = new BookingRepository();

    public Booking createBooking(Customer customer, Vehicle vehicle, LocalDateTime dateTime)
    {
        LocalDate date = dateTime.toLocalDate();
        double price = vehicle.getServicePrice();
        Booking booking = new Booking(0, customer, vehicle, date, "Service", price, false);


        bookingRepository.addBooking(booking);
        return booking;


    }

    public List<String> getAllBookings() {
        return List.of("other stuff and stuff");
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }

    public Map<String, LocalDateTime> getAvailableTimes()
    {
        return bookingRepository.getTimeTable();
    }
}