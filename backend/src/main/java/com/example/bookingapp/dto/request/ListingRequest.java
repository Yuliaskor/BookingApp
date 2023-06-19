package com.example.bookingapp.dto.request;

import com.example.bookingapp.dto.LocationDTO;
import com.example.bookingapp.enums.Category;
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
        @Schema(description = "listing photos", example = "[\"https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1175&q=80\"]")
        List<String> photos,
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
        @Schema(description = "listing available to", example = "2030-01-01")
        LocalDate availableTo,
        @NotNull(message = "Number of rooms must not be null")
        @Min(value = 1, message = "Number of rooms must be greater than 0")
        @Schema(description = "listing number of rooms", example = "2")
        Integer numberOfRooms,
        @NotNull(message = "Number of bathrooms must not be null")
        @Min(value = 1, message = "Number of bathrooms must be greater than 0")
        @Schema(description = "listing number of bathrooms", example = "1")
        Integer numberOfBathrooms,
        @NotNull(message = "Amenities must not be null")
        @Schema(description = "listing amenities", example = "[\"WIFI\", \"TV\"]")
        List<String> amenities,
        @NotNull(message = "Category must not be null")
        @Schema(description = "listing category", example = "BEACH")
        Category category
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
                numberOfRooms,
                numberOfBathrooms,
                amenities,
                photos.stream().filter(photo -> !photo.isBlank()).toList(),
                category
        );
    }
}
