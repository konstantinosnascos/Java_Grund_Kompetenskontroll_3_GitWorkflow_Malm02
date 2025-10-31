package com.example.service;

import java.util.List;
import java.util.Map;

public class AnalysisService {
    public AnalysisService(BookingService bookingService, CustomerService customerService) {
    }

    public List<String> getBookingsThisWeek() {
        return List.of("stuff and things");
    }

    public Map<String, Long> getTopServices() {
        return null;
    }

    public Map<String, Long> getBookingCountPerCustomer() {
        return null;
    }
}
