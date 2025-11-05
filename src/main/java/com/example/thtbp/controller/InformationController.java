package com.example.thtbp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.thtbp.repository.BannerRepository;
import com.example.thtbp.repository.ServiceRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class InformationController {
    @Autowired private BannerRepository bannerRepository;
    @Autowired private ServiceRepository serviceRepository;

    @GetMapping("/banner")
    public Map<String, Object> getBanner() {
        return Map.of("status", 0, "message", "Success", "data", bannerRepository.findAll());
    }

    @GetMapping("/services")
    public Map<String, Object> getServices() {
        return Map.of("status", 0, "message", "Success", "data", serviceRepository.findAll());
    }
}
