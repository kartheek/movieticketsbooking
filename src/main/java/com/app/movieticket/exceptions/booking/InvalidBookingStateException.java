package com.app.movieticket.exceptions.booking;

public class InvalidBookingStateException extends BookingException {
    public InvalidBookingStateException(String message) {
        super(message);
    }
}
