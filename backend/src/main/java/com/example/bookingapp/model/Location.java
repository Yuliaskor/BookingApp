package com.example.bookingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private long id;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String description;

}
