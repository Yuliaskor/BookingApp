package com.example.bookingapp.exceptions.host;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HostNotFoundException extends RuntimeException {
    public HostNotFoundException(long id) {
        super("Host with id " + id + " not found");
    }
}
