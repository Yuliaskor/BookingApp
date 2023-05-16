package com.example.bookingapp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private long id;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private double rating;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "listing_id")
    private Listing listing;
}
