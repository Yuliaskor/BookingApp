package com.example.bookingapp.dto;

import com.example.bookingapp.model.Location;
import io.swagger.v3.oas.annotations.media.Schema;

public record LocationDTO(
        @Schema(description = "location latitude", example = "51.11")
        double latitude,
        @Schema(description = "location longitude", example = "17.022222")
        double longitude,
        @Schema(description = "location city", example = "Wroclaw")
        String city,
        @Schema(description = "location country", example = "Poland")
        String country,
        @Schema(description = "location description", example = "Description")
        String description
) {
    public Location toEntity() {
        return new Location(
                latitude,
                longitude,
                city,
                country,
                description
        );
    }
}
