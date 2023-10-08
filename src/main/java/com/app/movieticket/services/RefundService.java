package com.app.movieticket.services;

import com.app.movieticket.models.Booking;
import com.app.movieticket.services.notifications.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class RefundService {
    private final NotificationService notificationService;

    public RefundService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void refundBooking(Booking booking) {
        // initiate refund
        notificationService.notifyRefundInitiated(
                String.format(
                        "Refund for your booking for %s made on %s has been initiated.",
                        booking.getShow().getMovie(),
                        booking.getShow().getStartTime()
                ),
                booking.getCustomer().getPhone()
        );
    }
}
