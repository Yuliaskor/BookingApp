package com.example.bookingapp.controller;

import com.example.bookingapp.dto.request.HostRequest;
import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.dto.response.HostDTO;
import com.example.bookingapp.dto.response.ListingDTO;
import com.example.bookingapp.service.HostService;
import com.example.bookingapp.service.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hosts")
@Tag(name = "Host Controller")
@CrossOrigin(origins = "http://localhost:3000")
public class HostController {

    private final HostService hostService;
    private final ListingService listingService;

    @GetMapping("/{id}")
    @Operation(summary = "Get host", description = "Get host by id")
    ResponseEntity<HostDTO> getHost(@PathVariable long id) {
        return ResponseEntity.ok(hostService.getHostById(id).toDTO());
    }

    // TODO: create new host on first login
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new host", description = "Add new host")
    ResponseEntity<HostDTO> addHost(@Valid @RequestBody HostRequest host) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hostService.addHost(host).toDTO());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete host", description = "Delete host by id")
    void deleteHost(@PathVariable long id) {
        hostService.deleteHost(id);
    }

    @PostMapping("/{id}/listings")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add new listing", description = "Add new listing to host listings")
    ResponseEntity<ListingDTO> addListing(@PathVariable("id") int hostId, @Valid @RequestBody ListingRequest listing) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listingService.addListing(hostId, listing).toDTO());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update host information", description = "Update host information by id")
    ResponseEntity<HostDTO> updateHost(@PathVariable long id, @Valid @RequestBody HostRequest host) {
        return ResponseEntity.ok(hostService.updateHost(id, host).toDTO());
    }

}
