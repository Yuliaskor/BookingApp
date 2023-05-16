package com.example.bookingapp.dto.response;

import java.util.List;

public record HostDTO(
        long id,
        String email,
        String name,
        String aboutMe,
        List<ListingDTO> listings
) {
}
