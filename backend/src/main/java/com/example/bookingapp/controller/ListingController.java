package com.example.bookingapp.controller;

import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.dto.request.ReservationRequest;
import com.example.bookingapp.dto.response.ListingDTO;
import com.example.bookingapp.dto.response.ReservationDTO;
import com.example.bookingapp.enums.Category;
import com.example.bookingapp.model.Listing;
import com.example.bookingapp.model.Reservation;
import com.example.bookingapp.service.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/listings")
@CrossOrigin(origins = "http://localhost:3000")
public class ListingController {

    private final ListingService listingService;

    @GetMapping()
    @Operation(summary = "Get listings", description = "Get all listings")
    ResponseEntity<Page<ListingDTO>> getListings(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) Category category) {
        return ResponseEntity.ok(listingService.getListings(category, pageable).map(Listing::toDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get listing", description = "Get listing by id")
    ResponseEntity<ListingDTO> getListing(@PathVariable long id) {
        return ResponseEntity.ok(listingService.getListingById(id).toDTO());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update listing information", description = "Update listing information by id")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<ListingDTO> updateListing(@PathVariable long id, @Valid @RequestBody ListingRequest listing) {
        return ResponseEntity.ok(listingService.updateListing(id, listing).toDTO());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete listing", description = "Delete listing by id")
    void deleteListing(@PathVariable long id) {
        listingService.deleteListing(id);
    }

    @GetMapping("/{id}/reservations")
    @Operation(summary = "Get reservations", description = "Get all reservations for listing")
    ResponseEntity<List<ReservationDTO>> getReservations(@PathVariable long id) {
        return ResponseEntity.ok(listingService.getReservationsByListingId(id)
                .stream()
                .map(Reservation::toDTO)
                .toList());
    }

    @PostMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new reservation", description = "Add new reservation to listing reservations")
    ResponseEntity<ReservationDTO> addReservation(@PathVariable("id") long listingId, @Valid @RequestBody ReservationRequest reservation) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listingService.addReservationToListing(listingId, reservation).toDTO());
    }

}
