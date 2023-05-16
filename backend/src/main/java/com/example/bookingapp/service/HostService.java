package com.example.bookingapp.service;

import com.example.bookingapp.dto.request.HostRequest;
import com.example.bookingapp.exceptions.host.HostAlreadyExistsException;
import com.example.bookingapp.exceptions.host.HostNotFoundException;
import com.example.bookingapp.model.Host;
import com.example.bookingapp.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;

    public Host getHostById(long id) {
        return hostRepository.findById(id)
                .orElseThrow(() -> new HostNotFoundException(id));
    }

    public Host addHost(HostRequest host) {
        if (hostRepository.existsByEmail(host.email())) {
            throw new HostAlreadyExistsException(host.email());
        }
        return hostRepository.save(host.toEntity());
    }

    public void deleteHost(long id) {
        if (!hostRepository.existsById(id)) {
            throw new HostNotFoundException(id);
        }
        hostRepository.deleteById(id);
    }
}
