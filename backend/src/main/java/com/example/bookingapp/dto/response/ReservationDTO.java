package com.example.bookingapp.dto.response;

import java.time.LocalDate;

public record ReservationDTO(
    long id,
    long listingId,
    LocalDate checkInDate,
    LocalDate checkOutDate
) {
}
