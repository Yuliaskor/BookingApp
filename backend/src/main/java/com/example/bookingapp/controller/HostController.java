package com.example.bookingapp.controller;

import com.example.bookingapp.dto.request.HostRequest;
import com.example.bookingapp.dto.response.HostDTO;
import com.example.bookingapp.service.HostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hosts")
public class HostController {

    private final HostService hostService;

    @GetMapping("/{id}")
    HostDTO getHost(@PathVariable long id) {
        return hostService.getHostById(id).toDTO();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    HostDTO addHost(@Valid @RequestBody HostRequest host) {
        return hostService.addHost(host).toDTO();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHost(@PathVariable long id) {
        hostService.deleteHost(id);
    }

}
