package com.example.bookingapp.dto.response;

import com.example.bookingapp.dto.LocationDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingDTO(
        @Schema(description = "listing id", example = "11")
        long id,
        @Schema(description = "listing host")
        ListingHostDTO host,
        @Schema(description = "listing title", example = "title")
        String title,
        @Schema(description = "listing description", example = "description")
        String description,
        @Schema(description = "listing location")
        LocationDTO location,
        @Schema(description = "listing photos", example = "[\"https://res.cloudinary.com/dovtmuel7/image/upload/v1686949278/av5lwqefqsjrt3wxtj7r.webp\"]")
        List<String> photos,
        @Schema(description = "listing price per night", example = "100.00")
        BigDecimal pricePerNight,
        @Schema(description = "listing max guests", example = "4")
        int maxGuests,
        @Schema(description = "listing available from", example = "2021-01-01")
        LocalDate availableFrom,
        @Schema(description = "listing available to", example = "2030-01-01")
        LocalDate availableTo,
        @Schema(description = "listing reservations")
        List<ReservationDTO> reservations,
        @Schema(description = "listing number of rooms", example = "2")
        int numberOfRooms,
        @Schema(description = "listing number of bathrooms", example = "1")
        int numberOfBathrooms,
        @Schema(description = "listing amenities", example = "[\"WIFI\", \"TV\"]")
        List<String> amenities,
        @Schema(description = "listing category", example = "Beach")
        String category,
        @Schema(description = "listing value", example = "4.5")
        Double rating
) {
}
