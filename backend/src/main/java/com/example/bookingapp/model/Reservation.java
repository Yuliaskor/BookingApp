package com.example.bookingapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "listing_id")
    private Listing listing;
    @Column(nullable = false)
    private LocalDate checkInDate;
    @Column(nullable = false)
    private LocalDate checkOutDate;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private String tenantName;
    @Column(nullable = false)
    private String tenantEmail;
    @Column(nullable = false)
    private int numberOfGuests;
}
