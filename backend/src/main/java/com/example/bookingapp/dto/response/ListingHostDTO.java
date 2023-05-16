package com.example.bookingapp.dto.response;

public record ListingHostDTO(
        long id,
        String email,
        String name,
        String aboutMe
) {
}
