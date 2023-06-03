package com.example.bookingapp.exceptions.host;

public class HostAlreadyExistsException extends RuntimeException {
    public HostAlreadyExistsException(String email) {
        super("Host with email " + email + " already exists");
    }
}
