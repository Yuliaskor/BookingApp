package com.example.bookingapp.service;

import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.exceptions.listing.ListingNotFoundException;
import com.example.bookingapp.model.Host;
import com.example.bookingapp.model.Listing;
import com.example.bookingapp.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final HostService hostService;

    public List<Listing> getListings(Pageable pageable) {
        return listingRepository.findAll(pageable).toList();
    }

    public Listing getListingById(long id) {
        return listingRepository.findById(id).orElseThrow(() -> new ListingNotFoundException(id));
    }

    public Listing addListing(ListingRequest listingRequest) {
        Host host = hostService.getHostById(listingRequest.hostId());
        Listing listing = listingRequest.toEntity();
        listing.setHost(host);
        return listingRepository.save(listing);
    }

    public void deleteListing(long id) {
        if (!listingRepository.existsById(id)) {
            throw new ListingNotFoundException(id);
        }
        listingRepository.deleteById(id);
    }
}
