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



//    //tar in en extra variabel(bookingtype) för att sätta pris på service.
//    public void createBooking(int id, String vehicleReg, LocalDateTime dateTime, String bookingType)
//    {
//        double price =0;
//
//        switch (bookingType.toUpperCase())
//        {
//            case "BESIKTNING":
//                price = pricingService.getBesiktningPris();
//                break;
//            case "SERVICE":
//                price = pricingService.calculateServicePrice(99);
//                break;
//            case "REPARATION":
//                price = 0;
//                break;
//            default:
//                throw new IllegalArgumentException("Ogiltig bokning:  välj service, besiktning, reparation");
//        }
//
//        Booking booking = new Booking(-1, , vehicleReg, dateTime.toLocalDate(), bookingType, price, false);
//
//        bookingRepository.addBooking(booking);
//        logger.info("Bokning skapad: {} som kommer kosta {}", bookingType, price);
//
//    }

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

        if ("REPARATION".equals(booking.getServiceType()) && reparationPrice != null)
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
    //Lägg till getBookingById()
    public Booking getBookingById(int id) {
        return bookingRepository.getBookings().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean editBooking(int id, Booking updatedBooking) {
        Booking existing = getBookingById(id);
        if (existing == null) {
            logger.warn("Ingen bokning med ID {} hittades för redigering.", id);
            return false;
        }

        // Behåll kund och status från originalbokningen
        //updatedBooking.setCustomer(existing.getCustomer());
        //updatedBooking.setCompleted(existing.isCompleted());

        boolean success = bookingRepository.editBooking(id, updatedBooking);
        if (success) {
            logger.info("Bokning med ID {} uppdaterades.", id);
        } else {
            logger.warn("Misslyckades med att uppdatera bokning med ID {}.", id);
        }
        return success;
    }


}