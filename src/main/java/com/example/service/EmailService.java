package com.example.service;

import com.example.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmailService implements NotificationService{
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendCompletionNotification(Booking booking)
    {
        String email = booking.getCustomer().getEmail();
        String message = String.format("Hej %s!\n\n" +
                        "Ditt fordon (%s) är nu klart för avhämtning.\n" +
                        "Typ av service: %s\n" +
                        "Totalt pris: %.2f kr\n\n" +
                        "Välkommen åter!\n" +
                        "Mvh Bilmeckarna AB",
                booking.getCustomer().getName(),
                booking.getVehicle().getModel(),
                booking.getServiceType(),
                booking.getPrice());
        System.out.println("Skickar mejl till: " + email);
        logger.info("Mail skickat till {}", email);
    }

    @Override
    public void sendBookingConfirmation(Booking booking)
    {
        String email = booking.getCustomer().getEmail();
        String name = booking.getCustomer().getName();
        String vehicleModel = booking.getVehicle().getModel();
        String regNum = booking.getVehicle().getRegNum();
        String serviceType = booking.getServiceType().toString().toLowerCase();
        String dateTime = booking.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 'kl.' HH:mm"));


        String message = String.format("Hej %s!\n\n" +
                        "Tack för din bokning hos Bilmeckarna AB!\n\n" +
                        "Din %s (%s) är bokad för %s den %s.\n\n" +
                        "Vänligen kom i tid och ha ditt boknings-ID: %d redo.\n\n" +
                        "Vi ser fram emot att hjälpa till!\n\n" +
                        "Med vänlig hälsning\n" +
                        "Bilmeckarna AB",
                name,
                vehicleModel,
                regNum,
                serviceType,
                dateTime,
                booking.getId()
        );
        System.out.println("Skickar bokningsmejl till: " + email);
        logger.info("Bokningsmejl skickat till {}", email);
    }
}
