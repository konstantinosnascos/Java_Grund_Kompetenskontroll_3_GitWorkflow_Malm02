package com.example.service;

import com.example.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

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
}
