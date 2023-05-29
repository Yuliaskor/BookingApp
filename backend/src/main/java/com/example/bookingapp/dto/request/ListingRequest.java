package com.example.bookingapp.dto.request;

import com.example.bookingapp.dto.LocationDTO;
import com.example.bookingapp.enums.Bed;
import com.example.bookingapp.enums.RoomType;
import com.example.bookingapp.model.Listing;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingRequest(
        @NotBlank(message = "Title must not be blank")
        String title,
        @NotBlank(message = "Description must not be blank")
        String description,
        @NotNull(message = "Location must not be null")
        LocationDTO location,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
        BigDecimal pricePerNight,
        @Min(value = 1, message = "Max guests must be greater than 0")
        @NotNull(message = "Max guests must not be null")
        Integer maxGuests,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate availableFrom,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate availableTo,
        @NotNull(message = "Room type must not be null")
        RoomType roomType,
        @NotNull(message = "Beds must not be null")
        List<Bed> beds,
        @NotNull(message = "Amenities must not be null")
        List<String> amenities
) {
    public Listing toEntity() {
        return new Listing(
                title,
                description,
                location,
                pricePerNight,
                maxGuests,
                availableFrom,
                availableTo,
                roomType,
                beds,
                amenities
        );
    }
}
