package com.app.movieticket.exceptions.accounts;

public class AccountAlreadyExistsException extends AccountsException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
