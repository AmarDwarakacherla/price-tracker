package com.pricetracker.price_tracker.controller;

import com.pricetracker.price_tracker.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class OtpTestController {

    private final OtpService otpService;

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        otpService.verifyOtp(email, otp);

        return "OTP verified successfully";
    }
}