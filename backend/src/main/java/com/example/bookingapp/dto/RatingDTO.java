package com.example.bookingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

public record RatingDTO(
        @Schema(description = "value", example = "5")
        @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
        Integer amount
) {
}
