package com.example.bookingapp.repository;

import com.example.bookingapp.enums.Category;
import com.example.bookingapp.model.Listing;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findAllByCategory(Category category, Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Listing> findWithLockingById(long id);
}
