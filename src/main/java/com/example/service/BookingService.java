package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    public void createBooking(String customerId, String vehicleReg, LocalDateTime time) {
    }

    public List<String> getAllBookings() {
        return List.of("other stuff and stuff");
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }


}
