package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookingService {

    public final BookingRepository bookingRepository = new BookingRepository();

    public void createBooking(int id, String vehicleReg, LocalDateTime dateTime)
    {
        LocalDate date = dateTime.toLocalDate();
        Booking booking = new Booking(id, vehicleReg, date, "Service", 0.0, false);

        bookingRepository.addBooking(booking);

        System.out.println("Bokning skapad:" + booking);

    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getBookings();
    }

    public boolean cancelBooking(String bookingId)
    {
        try {
            int id = Integer.parseInt(bookingId.trim());
            return bookingRepository.deleteBooking(id);
        }catch (NumberFormatException e) {
            System.out.println("Ogiltigt id");
            return false;
        }
    }

    public Map<String, LocalDateTime> getAvailableTimes()
    {
        return bookingRepository.getTimeTable();
    }
}