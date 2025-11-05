package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendBookingConfirmation(String email, String message) {
        System.out.println("Skickar e-post till " + email + ": " + message);
    }

}
