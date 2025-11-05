package com.example.service;

import com.example.model.Booking;

public interface NotificationService {
    void sendCompletionNotification(Booking booking);
}