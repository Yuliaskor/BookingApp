package com.example.bookingapp.dto.request;

import com.example.bookingapp.dto.LocationDTO;
import com.example.bookingapp.enums.Bed;
import com.example.bookingapp.enums.RoomType;
import com.example.bookingapp.model.Listing;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingRequest(
        @NotBlank(message = "Title must not be blank")
        @Schema(description = "listing title", example = "title")
        String title,
        @NotBlank(message = "Description must not be blank")
        @Schema(description = "listing description", example = "description")
        String description,
        @NotNull(message = "Location must not be null")
        @Schema(description = "listing location")
        LocationDTO location,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message ="Price per night must be greater than 0")
        @Schema(description = "listing price per night", example = "100.00")
        BigDecimal pricePerNight,
        @Min(value = 1, message = "Max guests must be greater than 0")
        @NotNull(message = "Max guests must not be null")
        @Schema(description = "listing max guests", example = "4")
        Integer maxGuests,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "listing available from", example = "2021-01-01")
        LocalDate availableFrom,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "listing available to", example = "2023-01-01")
        LocalDate availableTo,
        @NotNull(message = "Room type must not be null")
        @Schema(description = "listing room type", example = "ENTIRE_PLACE")
        RoomType roomType,
        @NotNull(message = "Beds must not be null")
        @Schema(description = "listing beds", example = "[\"SINGLE_BED\", \"DOUBLE_BED\"]")
        List<Bed> beds,
        @NotNull(message = "Amenities must not be null")
        @Schema(description = "listing amenities", example = "[\"WIFI\", \"TV\"]")
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
