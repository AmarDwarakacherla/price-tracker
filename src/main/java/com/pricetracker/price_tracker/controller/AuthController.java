package com.pricetracker.price_tracker.controller;

import com.pricetracker.price_tracker.dto.SignupRequest;
import com.pricetracker.price_tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(
                "Signup successful. OTP sent to your email."
        );
    }
}