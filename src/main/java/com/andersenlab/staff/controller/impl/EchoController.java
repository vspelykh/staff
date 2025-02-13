package com.andersenlab.staff.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping("/api/echo")
@RequiredArgsConstructor
public class EchoController {

    @GetMapping
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("Staff management API is working");
    }
}
