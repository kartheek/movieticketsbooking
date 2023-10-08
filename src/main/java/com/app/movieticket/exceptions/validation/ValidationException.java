package com.app.movieticket.exceptions.validation;

import com.bookmyshow.app.exceptions.BookMyShowException;

public class ValidationException extends BookMyShowException {
    public ValidationException(String message) {
        super(message);
    }
}
