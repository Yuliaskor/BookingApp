package com.example.bookingapp.dto.request;

import com.example.bookingapp.model.Listing;
import com.example.bookingapp.model.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "Tenant name must not be blank")
        String tenantName,
        @Email(message = "Email must be valid email address")
        @NotBlank(message = "Email must not be blank")
        String tenantEmail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Check in date must not be empty")
        LocalDate checkInDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Check out date must not be empty")
        LocalDate checkOutDate,
        @Min(value = 1, message = "Number of guests must be at least 1")
        int numberOfGuests
) {
    public Reservation toEntity(Listing listing, BigDecimal pricePerNight) {
        return new Reservation(
                listing,
                checkInDate,
                checkOutDate,
                pricePerNight.multiply(BigDecimal.valueOf(numberOfGuests)),
                tenantName,
                tenantEmail,
                numberOfGuests
        );
    }
}
