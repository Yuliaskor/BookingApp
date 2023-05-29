package com.example.bookingapp.exceptions;

import java.time.LocalDateTime;

record ErrorResponse(
    int status,
    String message,
    LocalDateTime timestamp
) {

}
