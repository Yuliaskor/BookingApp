package com.example.bookingapp.model;

import com.example.bookingapp.dto.LocationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@Getter
@Setter
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

    public Location(double latitude, double longitude, String city, String country, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.description = description;
    }


    public LocationDTO toDTO() {
        return new LocationDTO(
                this.latitude,
                this.longitude,
                this.city,
                this.country,
                this.description
        );
    }
}
