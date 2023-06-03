package com.example.bookingapp.exceptions.reservation;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(long id) {
        super("Reservation with id " + id + " not found");
    }

    public ReservationNotFoundException(long listingId, long reservationId) {
        super("Reservation with id " + reservationId + " not found for listing with id " + listingId);
    }
}

