package com.example.bookingapp.controller;

import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.dto.response.ListingDTO;
import com.example.bookingapp.model.Listing;
import com.example.bookingapp.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping()
    List<ListingDTO> getListings(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return listingService.getListings(pageable)
                .stream()
                .map(Listing::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    ListingDTO getListing(@PathVariable long id) {
        return listingService.getListingById(id).toDTO();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ListingDTO addListing(@Valid @RequestBody ListingRequest listing) {
        return listingService.addListing(listing).toDTO();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHost(@PathVariable long id) {
        listingService.deleteListing(id);
    }
}
