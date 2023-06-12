package com.example.bookingapp.email;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationConfirmation(
        String listingName,
        String tenantName,
        BigDecimal totalPrice,
        LocalDate checkIn,
        LocalDate checkOut,
        int numberOfGuests
) {
}
