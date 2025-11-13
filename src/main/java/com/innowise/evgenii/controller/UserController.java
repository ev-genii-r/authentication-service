package com.innowise.evgenii.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo(Principal principal) {
        return ResponseEntity.ok("User info for: " + principal.getName());
    }

    @GetMapping("/{userId}/info")
    public ResponseEntity<String> getUserInfoById(@PathVariable Long userId, Principal principal) {

        return ResponseEntity.ok("User info for ID: " + userId);
    }
}