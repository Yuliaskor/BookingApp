package com.example.bookingapp.exceptions.host;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HostAlreadyExistsException extends RuntimeException {
    public HostAlreadyExistsException(String email) {
        super("Host with email " + email + " already exists");
    }
}
