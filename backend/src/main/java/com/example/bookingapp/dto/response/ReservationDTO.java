package com.example.bookingapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationDTO(
        @Schema(description = "reservation id", example = "22")
        long id,
        @Schema(description = "listing id", example = "11")
        long listingId,
        @Schema(description = "tenant name", example = "Alex Smith")
        String tenantName,
        @Schema(description = "tenant email", example = "alexsmith@booking.com")
        String tenantEmail,
        @Schema(description = "reservation total price", example = "200.00")
        BigDecimal totalPrice,
        @Schema(description = "reservation check in date", example = "2023-05-15")
        LocalDate checkInDate,
        @Schema(description = "reservation check out date", example = "2023-05-22")
        LocalDate checkOutDate,
        @Schema(description = "reservation number of guests", example = "2")
        int numberOfGuests
) {
}
