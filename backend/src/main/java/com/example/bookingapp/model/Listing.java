package com.example.bookingapp.model;

import com.example.bookingapp.enums.Bed;
import com.example.bookingapp.enums.RoomType;
import jakarta.persistence.*;

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
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String description;
    @OneToOne
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
    @Column(nullable = false)
    private LocalDate availableFrom;
    @Column(nullable = false)
    private LocalDate availableTo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;
    @OneToMany
    @JoinTable(name = "listings_ratings", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "rating_id"))
    private List<Rating> ratings;
    @OneToMany
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
    private List<String> amenities;
}
