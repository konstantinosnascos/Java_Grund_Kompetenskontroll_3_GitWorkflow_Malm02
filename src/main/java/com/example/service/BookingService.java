package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;

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

    public void createBooking(int id, String vehicleReg, LocalDateTime dateTime)
    {
        LocalDate date = dateTime.toLocalDate();
        Booking booking = new Booking(id, vehicleReg, date, "Service", 0.0, false);

        bookingRepository.addBooking(booking);

        System.out.println("Bokning skapad:" + booking);

    }

    //tar in en extra variabel(bookingtype) för att sätta pris på service.
    public void createBooking(int id, String vehicleReg, LocalDateTime dateTime, String bookingType)
    {
        double price =0;

        switch (bookingType.toUpperCase())
        {
            case "BESIKTNING":
                price = pricingService.getBesiktningPris();
                break;
            case "SERVICE":
                price = pricingService.calculateServicePrice(99);
                break;
            case "REPARATION":
                price = 0;
                break;
            default:
                throw new IllegalArgumentException("Ogiltig bokning:  välj service, besiktning, reparation");
        }

        Booking booking = new Booking(
                -1, vehicleReg, dateTime.toLocalDate(), bookingType, price, false
        );

        bookingRepository.addBooking(booking);
        logger.info("Bokning skapad: {} som kommer kosta {}", bookingType, price);

    }

    public boolean completeBooking(int bookingId, Double reparationPrice)
    {
        Booking booking = bookingRepository.getBookings().get(bookingId);
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

        if ("REPARATION".equals(booking.getBookingType()) && reparationPrice != null)
        {
            if (reparationPrice<= 0)
            {
                logger.warn("Pris måste va högre än 0.");
                return false;
            }
            booking.setPrice(reparationPrice);
            logger.info("Pris är satt till {}", reparationPrice);
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