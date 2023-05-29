package com.example.bookingapp.initialization;

import com.example.bookingapp.model.Host;
import com.example.bookingapp.repository.HostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!prod")
@RequiredArgsConstructor
@Component
public class HostDbInit {
    private final HostRepository hostRepository;

    @PostConstruct
    void addTestHosts() {
        List<Host> hosts = List.of(
                new Host(1, "test1@test.com", "John Doe", "about me1", List.of()),
                new Host(2, "test2@test.com", "Ulices Gottlieb", "about me2", List.of()),
                new Host(3, "test3@test.com", "Colten Emard", "about me3", List.of()),
                new Host(4, "test4@test.com", "Matilda Rosenbaum", "about me4", List.of()),
                new Host(5, "test5@test.com", "Barrett Wolf", "about me5", List.of())
        );
        hosts.forEach(host -> hostRepository.findById(host.getId()).orElseGet(() -> hostRepository.save(host)));
    }
}
