package com.example.bookingapp.dto.request;

import com.example.bookingapp.model.Host;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record HostRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid email address")
        @Schema(description = "host email", example = "johndoe@booking.com")
        String email,
        @NotBlank(message = "Name must not be blank")
        @Schema(description = "host name", example = "John Doe")
        String name,
        @NotBlank(message = "About me must not be blank")
        @Schema(description = "host about me", example = "About me")
        String aboutMe
) {
    public Host toEntity() {
        return new Host(email, name, aboutMe);
    }
}
