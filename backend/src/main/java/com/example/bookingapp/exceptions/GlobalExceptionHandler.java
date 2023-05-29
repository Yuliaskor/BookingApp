package com.example.bookingapp.exceptions;

import com.example.bookingapp.exceptions.host.HostAlreadyExistsException;
import com.example.bookingapp.exceptions.host.HostNotFoundException;
import com.example.bookingapp.exceptions.listing.ListingNotFoundException;
import com.example.bookingapp.exceptions.reservation.ReservationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(HostAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleHostAlreadyExistsException(HostAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(HostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHostNotFoundException(HostNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleListingNotFoundException(ListingNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

}
