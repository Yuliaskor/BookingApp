package com.example.bookingapp.model;

import com.example.bookingapp.dto.response.ReservationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
@Setter
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

    public Reservation(Listing listing, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal totalPrice, String tenantName, String tenantEmail, int numberOfGuests) {
        this.listing = listing;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.tenantName = tenantName;
        this.tenantEmail = tenantEmail;
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationDTO toDTO() {
        return new ReservationDTO(
                this.id,
                this.listing.getId(),
                this.tenantName,
                this.tenantEmail,
                this.totalPrice,
                this.checkInDate,
                this.checkOutDate,
                this.numberOfGuests
        );
    }
}
