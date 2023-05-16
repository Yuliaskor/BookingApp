package com.example.bookingapp.exceptions.listing;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(long id) {
        super("Listing with id " + id + " not found");
    }
}
