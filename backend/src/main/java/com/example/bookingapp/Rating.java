package com.example.bookingapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private long id;
    @NotNull
    private String author;
    @NotNull
    private double rating;
    @NotNull
    private String comment;
    @NotNull
    @CreationTimestamp
    private LocalDate createdAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "listing_id")
    private Listing listing;
}
