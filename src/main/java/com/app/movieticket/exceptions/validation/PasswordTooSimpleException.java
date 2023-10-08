package com.app.movieticket.exceptions.validation;

public class PasswordTooSimpleException extends ValidationException {
    public PasswordTooSimpleException(String message) {
        super(message);
    }
}
