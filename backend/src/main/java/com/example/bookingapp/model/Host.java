package com.example.bookingapp.model;

import com.example.bookingapp.dto.request.HostRequest;
import com.example.bookingapp.dto.response.HostDTO;
import com.example.bookingapp.dto.response.ListingHostDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hosts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String aboutMe;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "host_listings", joinColumns = @JoinColumn(name = "host_id"), inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private List<Listing> listings;

    public Host(String email, String name, String aboutMe) {
        this.email = email;
        this.name = name;
        this.aboutMe = aboutMe;
        this.listings = new ArrayList<>();
    }

    public HostDTO toDTO() {
        return new HostDTO(
                this.id,
                this.email,
                this.name,
                this.aboutMe,
                this.listings.stream().map(Listing::toDTO).toList()
        );
    }

    public ListingHostDTO toListingDTO() {
        return new ListingHostDTO(
                this.id,
                this.email,
                this.name,
                this.aboutMe
        );
    }

    public void addListing(Listing listing) {
        this.listings.add(listing);
    }

    public void updateFromDTO(HostRequest hostRequest) {
        this.email = hostRequest.email();
        this.name = hostRequest.name();
        this.aboutMe = hostRequest.aboutMe();
    }
}
