package com.example.bookingapp.repository;

import com.example.bookingapp.enums.Category;
import com.example.bookingapp.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findAllByCategory(Category category, Pageable pageable);

}
