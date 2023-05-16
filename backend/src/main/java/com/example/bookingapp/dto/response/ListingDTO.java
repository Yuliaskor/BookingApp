package com.example.bookingapp.dto.response;

import com.example.bookingapp.dto.LocationDTO;
import com.example.bookingapp.enums.Bed;
import com.example.bookingapp.enums.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingDTO(
        long id,
        ListingHostDTO host,
        String title,
        String description,
        LocationDTO location,
        List<String> photos,
        BigDecimal pricePerNight,
        int maxGuests,
        LocalDate availableFrom,
        LocalDate availableTo,
        RoomType roomType,
        List<ReservationDTO> reservations,
        List<Bed> beds,
        List<String> amenities

) {
}
