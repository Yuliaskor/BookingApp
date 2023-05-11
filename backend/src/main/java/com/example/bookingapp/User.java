package com.example.bookingapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @Lob
    @Column(columnDefinition = "text")
    @NotNull
    private String aboutMe;
    @OneToMany
    @JoinTable(name = "user_listings", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private List<Listing> listings;
}
