package com.andersenlab.staff.controller;

import com.andersenlab.staff.service.DataFillerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("test")
@RestController
@RequestMapping("/api/v1/fill-data")
@RequiredArgsConstructor
public class DataFillerController {

    private final DataFillerService dataFillerService;

    @PostMapping("/{numberOfEmployees}")
    public ResponseEntity<String> fillDatabase(@PathVariable int numberOfEmployees) {
        dataFillerService.fillDatabaseWithFakeData(numberOfEmployees);
        return ResponseEntity.ok("Database filled with " + numberOfEmployees + " employees.");
    }
}
