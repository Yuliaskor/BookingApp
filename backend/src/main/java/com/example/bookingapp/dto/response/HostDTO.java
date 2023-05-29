package com.example.bookingapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HostDTO(
        @Schema(description = "host id", example = "15")
        long id,
        @Schema(description = "host email", example = "johndoe@booking.com")
        String email,
        @Schema(description = "host name", example = "John Doe")
        String name,
        @Schema(description = "host about me", example = "About me")
        String aboutMe,
        @Schema(description = "host listings")
        List<ListingDTO> listings
) {
}
