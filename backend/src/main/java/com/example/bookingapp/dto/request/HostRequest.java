package com.example.bookingapp.dto.request;

import com.example.bookingapp.model.Host;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record HostRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid email address")
        String email,
        @NotBlank(message = "Name must not be blank")
        String name,
        @NotBlank(message = "About me must not be blank")
        String aboutMe
) {
    public Host toEntity() {
        return new Host(email, name, aboutMe);
    }
}
