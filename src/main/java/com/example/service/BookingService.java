package com.example.service;

import com.example.model.Booking;
import com.example.model.Customer;
import com.example.model.ServiceType;
import com.example.model.Vehicle;
import com.example.repository.BookingRepository;
import com.example.service.PricingService;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookingService {

    public final BookingRepository bookingRepository = new BookingRepository();
    public final PricingService pricingService = new PricingService();

    public Booking createBooking(Customer customer, Vehicle vehicle, LocalDateTime dateTime, ServiceType serviceType)
    {
        LocalDate date = dateTime.toLocalDate();
        double price = vehicle.getServicePrice();

        switch(serviceType)
        {
            case SERVICE:
                price = pricingService.calculateServicePrice(vehicle.getYear());
                break;
            case BESIKTNING:
                price = pricingService.getBesiktningPris();
                break;
            case REPARATION:
                double userPrice = 0;
                price = pricingService.setReparationPrice(userPrice);
                break;

        }
        Booking booking = new Booking(0, customer, vehicle, date, serviceType, price, false);


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