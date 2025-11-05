package com.example.service;

import com.example.model.Booking;
import com.example.model.Customer;
import com.example.model.ServiceType;
import com.example.model.Vehicle;
import com.example.repository.BookingRepository;
import com.example.service.PricingService;

import java.awt.print.Book;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private PricingService pricingService = new PricingService();
    public final BookingRepository bookingRepository = new BookingRepository();

    public Booking createBooking(Customer customer, Vehicle vehicle, LocalDateTime dateTime, ServiceType serviceType, String selectedTime)
    {

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
        Booking booking = new Booking(0, customer, vehicle, dateTime, serviceType, price, false);


        bookingRepository.addBooking(booking);
        bookingRepository.removeTime(selectedTime);
        return booking;


    }

    public boolean completeBooking(int bookingId, Double reparationPrice)
    {
        Booking booking = bookingRepository.getBookings().stream()
                .filter(b-> b.getId() ==bookingId)
                .findFirst()
                .orElse(null);


        if (booking== null)
        {
            logger.warn("Boknings ID: {} hittades inte", bookingId);
            return false;
        }
        if (booking.isCompleted())
        {
            logger.warn("bokningen är redan avslutad.");
            return false;
        }

        if (booking.getServiceType() == ServiceType.REPARATION && reparationPrice != null)
        {
            if (reparationPrice <= 0) {
                logger.warn("Pris måste vara högre än 0.");
                return false;
            }
            booking.setPrice(reparationPrice);
            logger.info("Reparationspris satt till {} kr", reparationPrice);
        }

        booking.setCompleted(true);
        logger.info("Bokning med ID {} är klar.", bookingId);
        return true;
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