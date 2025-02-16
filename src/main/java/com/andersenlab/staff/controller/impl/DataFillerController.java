package com.andersenlab.staff.controller.impl;

import com.andersenlab.staff.config.security.UserAccess;
import com.andersenlab.staff.service.DataFillerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping("/api/v1/fill-data")
@RequiredArgsConstructor
public class DataFillerController {

    private final DataFillerService dataFillerService;

    @UserAccess
    @PostMapping("/{numberOfEmployees}")
    public ResponseEntity<String> fillDatabase(@PathVariable int numberOfEmployees) {
        dataFillerService.fillDatabaseWithFakeData(numberOfEmployees);
        return ResponseEntity.ok("Database filled with " + numberOfEmployees + " employees.");
    }
}
