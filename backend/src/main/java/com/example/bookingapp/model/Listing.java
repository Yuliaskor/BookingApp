package com.example.bookingapp.model;

import com.example.bookingapp.dto.LocationDTO;
import com.example.bookingapp.dto.request.ListingRequest;
import com.example.bookingapp.dto.response.ListingDTO;
import com.example.bookingapp.enums.Bed;
import com.example.bookingapp.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listings")
@NoArgsConstructor
@Getter
@Setter
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Host host;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @ElementCollection
    @CollectionTable(name = "photos", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "photo_url")
    private List<String> photos;
    @Column(nullable = false)
    private BigDecimal pricePerNight;
    @Column(nullable = false)
    private int maxGuests;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "listings_ratings", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "rating_id"))
    private List<Rating> ratings;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "listings_reservations", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservations;
    @ElementCollection
    @CollectionTable(name = "listings_beds", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "bed", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Bed> beds;
    @ElementCollection
    @CollectionTable(name = "listings_amenities", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "amenity", nullable = false)
    private List<String> amenities = new ArrayList<>();

    public Listing(String title, String description, LocationDTO location, BigDecimal pricePerNight, int maxGuests, LocalDate availableFrom, LocalDate availableTo, RoomType roomType, List<Bed> beds, List<String> amenities) {
        this.title = title;
        this.description = description;
        this.location = location.toEntity();
        this.pricePerNight = pricePerNight;
        this.maxGuests = maxGuests;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.roomType = roomType;
        this.beds = beds;
        this.amenities = amenities;
        this.photos = List.of();
        this.reservations = List.of();
        this.ratings = List.of();
    }

    public ListingDTO toDTO() {
        return new ListingDTO(
                this.id,
                this.host.toListingDTO(),
                this.title,
                this.description,
                this.location.toDTO(),
                this.photos,
                this.pricePerNight,
                this.maxGuests,
                this.availableFrom,
                this.availableTo,
                this.roomType,
                this.reservations.stream().map(Reservation::toDTO).toList(),
                this.beds,
                this.amenities
        );
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void checkIfReservationPossible(Reservation reservation) {
        checkNumberOfGuests(reservation);
        checkCheckInDate(reservation);
        checkCheckoutDate(reservation);
        checkIfNoOverlappingReservations(reservation);
    }

    private void checkIfNoOverlappingReservations(Reservation newReservation) {
        reservations.stream()
                .filter(reservation -> doesReservationOverlapWithAnother(newReservation, reservation))
                .findFirst()
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Reservation overlaps with another reservation");
                });
    }

    private static boolean doesReservationOverlapWithAnother(Reservation newReservation, Reservation reservation) {
        return (reservation.getCheckInDate().isBefore(newReservation.getCheckOutDate()) || reservation.getCheckInDate().isEqual(newReservation.getCheckOutDate()))
                && (reservation.getCheckOutDate().isAfter(newReservation.getCheckInDate()) || reservation.getCheckOutDate().isEqual(newReservation.getCheckInDate()));
    }

    private void checkCheckoutDate(Reservation reservation) {
        if (availableTo != null && availableTo.isBefore(reservation.getCheckOutDate())) {
            throw new IllegalArgumentException(String.format("Check-out date for listing cannot be after %s", availableTo));
        }
    }

    private void checkCheckInDate(Reservation reservation) {
        if (reservation.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation cannot be made for past dates");
        }
        if (availableFrom != null && availableFrom.isAfter(reservation.getCheckInDate())) {
            throw new IllegalArgumentException(String.format("Check-in date for listing cannot be before %s", availableFrom));
        }
    }

    private void checkNumberOfGuests(Reservation reservation) {
        if (maxGuests < reservation.getNumberOfGuests()) {
            throw new IllegalArgumentException(String.format("Listing cannot accommodate %d guests", reservation.getNumberOfGuests()));
        }
    }

    public void update(ListingRequest listing) {
        this.title = listing.title();
        this.description = listing.description();
        this.location = listing.location().toEntity();
        this.pricePerNight = listing.pricePerNight();
        this.maxGuests = listing.maxGuests();
        this.availableFrom = listing.availableFrom();
        this.availableTo = listing.availableTo();
        this.roomType = listing.roomType();
        this.beds = listing.beds();
        this.amenities = listing.amenities();
    }
}
