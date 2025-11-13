package com.innowise.evgenii.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("All users list (admin only)");
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        return ResponseEntity.ok("Admin statistics");
    }
}
