package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BookingService {
    public void createBooking(String customerId, String vehicleReg, LocalDateTime time)
    {
        Booking booking = new Booking(customerId, vehicleReg, time);

        BookingRepository.save(time, booking);
    }

    public List<String> getAllBookings() {
        return List.of("other stuff and stuff");
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }
}
