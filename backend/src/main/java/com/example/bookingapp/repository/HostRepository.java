package com.example.bookingapp.repository;

import com.example.bookingapp.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
    boolean existsByEmail(String email);
}
