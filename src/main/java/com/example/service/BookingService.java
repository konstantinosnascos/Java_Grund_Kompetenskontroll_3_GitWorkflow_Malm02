package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BookingService {

    public final BookingRepository bookingRepository = new BookingRepository();

    public void createBooking(int id, String vehicleReg, LocalDateTime dateTime)
    {
        LocalDate date = dateTime.toLocalDate();

        Booking booking = new Booking(id, vehicleReg, date, "Service", 0.0);

        bookingRepository.addBooking(booking);

        System.out.println("Bokning skapad:" + booking);


    }

    public List<String> getAllBookings() {
        return List.of("other stuff and stuff");
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }


}
