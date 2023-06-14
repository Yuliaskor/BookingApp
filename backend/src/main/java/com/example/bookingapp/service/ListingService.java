package com.example.bookingapp.service;

import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.dto.request.ReservationRequest;
import com.example.bookingapp.email.EmailService;
import com.example.bookingapp.email.ReservationConfirmation;
import com.example.bookingapp.exceptions.listing.ListingNotFoundException;
import com.example.bookingapp.model.Host;
import com.example.bookingapp.model.Listing;
import com.example.bookingapp.model.Reservation;
import com.example.bookingapp.repository.HostRepository;
import com.example.bookingapp.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final HostService hostService;
    private final HostRepository hostRepository;
    private final EmailService emailService;

    public Page<Listing> getListings(Pageable pageable) {
        return listingRepository.findAll(pageable);
    }

    public Listing getListingById(long id) {
        return listingRepository.findById(id).orElseThrow(() -> new ListingNotFoundException(id));
    }

    public Listing addListing(int hostId, ListingRequest listingRequest) {
        Host host = hostService.getHostById(hostId);
        Listing listing = listingRequest.toEntity();
        listing.setHost(host);
        host.addListing(listing);
        hostRepository.save(host);
        return host.getListings().get(host.getListings().size() - 1);
    }

    public void deleteListing(long id) {
        if (!listingRepository.existsById(id)) {
            throw new ListingNotFoundException(id);
        }
        listingRepository.deleteById(id);
    }

    public Reservation addReservationToListing(long listingId, ReservationRequest reservationDTO) {
        checkDates(reservationDTO.checkInDate(), reservationDTO.checkOutDate());
        Listing listing = getListingById(listingId);
        Reservation reservation = reservationDTO.toEntity(listing, listing.getPricePerNight());
        listing.checkIfReservationPossible(reservation);
        listing.addReservation(reservation);
        sendConfirmationOfReservationEmail(reservationDTO, listing, reservation);
        return listingRepository.save(listing)
                .getReservations()
                .get(listing.getReservations().size() - 1);
    }

    private void checkDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public List<Reservation> getReservationsByListingId(long listingId) {
        return getListingById(listingId).getReservations();
    }

    private void sendConfirmationOfReservationEmail(ReservationRequest reservationDTO, Listing listing, Reservation reservation) {
        ReservationConfirmation confirmation = new ReservationConfirmation(
                listing.getTitle(),
                reservation.getTenantName(),
                reservation.getTotalPrice(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getNumberOfGuests()
        );
        emailService.sendConfirmationOfReservation(reservationDTO.tenantEmail(), "Reservation confirmation", confirmation);
    }
}
