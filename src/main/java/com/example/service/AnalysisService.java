package com.example.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.model.Booking;
import com.example.model.ServiceType;
import java.util.*;


public class AnalysisService {
    private final BookingService bookingService;
    private final CustomerService customerService;

    public AnalysisService(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
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

    public Map<String, Long> getBookingCountByType()
    {
        return bookingService.getAllBookings().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getServiceType().toString(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}
