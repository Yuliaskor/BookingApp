package com.example.bookingapp.dto.request;

import com.example.bookingapp.model.Listing;
import com.example.bookingapp.model.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "Tenant name must not be blank")
        @Schema(description = "tenant name", example = "Alex Smith")
        String tenantName,
        @Email(message = "Email must be valid email address")
        @NotBlank(message = "Email must not be blank")
        @Schema(description = "tenant email", example = "alexsmith@booking.com")
        String tenantEmail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Check in date must not be empty")
        @Schema(description = "reservation check in date", example = "2023-05-15")
        LocalDate checkInDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Check out date must not be empty")
        @Schema(description = "reservation check out date", example = "2023-05-22")
        LocalDate checkOutDate,
        @Min(value = 1, message = "Number of guests must be at least 1")
        @Schema(description = "reservation number of guests", example = "2")
        int numberOfGuests
) {
    public Reservation toEntity(Listing listing, BigDecimal pricePerNight) {
        return new Reservation(
                listing,
                checkInDate,
                checkOutDate,
                pricePerNight.multiply(BigDecimal.valueOf(numberOfGuests)).multiply(getNumberOfNights()),
                tenantName,
                tenantEmail,
                numberOfGuests
        );
    }

    private BigDecimal getNumberOfNights() {
        return BigDecimal.valueOf(checkOutDate.toEpochDay() - checkInDate.toEpochDay() + 1);
    }
}
