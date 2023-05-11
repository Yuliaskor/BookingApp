package com.example.bookingapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private long id;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @Lob
    @Column(columnDefinition = "text")
    @NotNull
    private String description;

}
