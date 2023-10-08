package com.app.movieticket.exceptions.validation;

public class UsernameTakenException extends ValidationException {
    public UsernameTakenException(String message) {
        super(message);
    }
}
