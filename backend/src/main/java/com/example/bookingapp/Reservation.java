package com.example.bookingapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private LocalDate checkInDate;
    @NotNull
    private LocalDate checkOutDate;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private String tenantName;
    @NotNull
    private String tenantEmail;
    @NotNull
    private int numberOfGuests;
}
