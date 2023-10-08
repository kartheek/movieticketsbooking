package com.app.movieticket.exceptions.booking;

public class SeatsNotAvailableException extends BookingException {
    public SeatsNotAvailableException(String message) {
        super(message);
    }
}
