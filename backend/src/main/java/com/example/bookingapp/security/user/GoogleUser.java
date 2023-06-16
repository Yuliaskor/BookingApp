package com.example.bookingapp.security.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleUser {
    private final String id;
    private final String email;

    public static GoogleUser fromTokenPayload(GoogleIdToken.Payload payload) {
        return new GoogleUser(
                payload.getSubject(),
                payload.getEmail()
        );
    }
}
