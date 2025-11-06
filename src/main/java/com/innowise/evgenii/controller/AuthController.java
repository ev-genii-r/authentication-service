package com.innowise.evgenii.controller;

import com.innowise.evgenii.dto.TokenRefreshRequest;
import com.innowise.evgenii.dto.TokenResponse;
import com.innowise.evgenii.dto.UserLoginRequest;
import com.innowise.evgenii.entity.Role;
import com.innowise.evgenii.entity.User;
import com.innowise.evgenii.service.JwtUtil;
import com.innowise.evgenii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        if (!jwtTokenUtil.validateToken(request.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtTokenUtil.getEmailFromToken(request.getRefreshToken());
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtTokenUtil.generateAccessToken(user);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = jwtTokenUtil.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserLoginRequest request) {
        User user = userService.registerUser(request.getEmail(), request.getPassword(), Role.ROLE_USER);
        return ResponseEntity.ok(user);
    }

}
