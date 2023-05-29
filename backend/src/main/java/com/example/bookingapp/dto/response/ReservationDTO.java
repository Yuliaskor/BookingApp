package com.example.bookingapp.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationDTO(
    long id,
    long listingId,
    String tenantName,
    String tenantEmail,
    BigDecimal totalPrice,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    int numberOfGuests
) {
}
