package com.pricetracker.price_tracker.controller;

import com.pricetracker.price_tracker.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class EmailTestController {

    private final OtpService otpService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {

        otpService.generateOtp(email);

        return "OTP sent successfully";
    }
}