package com.example.bookingapp.exceptions.listing;


public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(long id) {
        super("Listing with id " + id + " not found");
    }
}
