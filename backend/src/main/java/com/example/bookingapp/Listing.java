package com.example.bookingapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private User host;
    @NotNull
    private String title;
    @Lob
    @Column(columnDefinition = "text")
    @NotNull
    private String description;
    @OneToOne
    @NotNull
    @JoinColumn(name = "location_id")
    private Location location;
    @ElementCollection
    @CollectionTable(name = "photos", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "photo_url")
    private List<String> photos;
    @NotNull
    private BigDecimal pricePerNight;
    @NotNull
    private int maxGuests;
    @NotNull
    private LocalDate availableFrom;
    @NotNull
    private LocalDate availableTo;
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoomType roomType;
    @OneToMany
    @JoinTable(name = "listings_ratings", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "rating_id"))
    private List<Rating> ratings;
    @OneToMany
    @JoinTable(name = "listings_reservations", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservations;
    @ElementCollection
    @CollectionTable(name = "listings_beds", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "bed")
    @Enumerated(EnumType.STRING)
    private List<Bed> beds;
    @ElementCollection
    @CollectionTable(name = "listings_amenities", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "amenity")
    private List<String> amenities;
}
