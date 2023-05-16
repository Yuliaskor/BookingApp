package com.example.bookingapp.dto;

import com.example.bookingapp.model.Location;

public record LocationDTO(
        double latitude,
        double longitude,
        String city,
        String country,
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
