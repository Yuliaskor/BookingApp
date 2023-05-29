package com.example.bookingapp.exceptions.host;


public class HostNotFoundException extends RuntimeException {
    public HostNotFoundException(long id) {
        super("Host with id " + id + " not found");
    }
}
