package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.service.LocationService;

@CrossOrigin
@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/states")
    public List<?> getStatesAndDistricts() {
        return locationService.getAllStatesAndDistricts();
    }

    @GetMapping("/districts")
    public List<String> getDistricts(@RequestParam String state) {
        return locationService.getDistrictsByState(state);
    }
}

