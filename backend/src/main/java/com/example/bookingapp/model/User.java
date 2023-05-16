package com.example.bookingapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String aboutMe;
    @OneToMany
    @JoinTable(name = "user_listings", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private List<Listing> listings;
}
