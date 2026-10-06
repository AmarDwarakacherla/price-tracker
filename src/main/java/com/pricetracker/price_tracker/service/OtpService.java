package com.pricetracker.price_tracker.service;

import com.pricetracker.price_tracker.entity.EmailVerification;
import com.pricetracker.price_tracker.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public String generateOtp(String email) {

        String otp = String.format(
                "%06d",
                new Random().nextInt(1_000_000)
        );

        EmailVerification verification = EmailVerification.builder()
                .email(email)
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        emailVerificationRepository.save(verification);
        emailService.sendOtpEmail(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        EmailVerification verification =
                emailVerificationRepository
                        .findByEmailAndOtp(email, otp)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid OTP"));

        if (verification.isVerified()) {
            throw new RuntimeException("OTP already used");
        }

        if (LocalDateTime.now().isAfter(verification.getExpiresAt())) {
            throw new RuntimeException("OTP has expired");
        }
        verification.setVerified(true);
        emailVerificationRepository.save(verification);
        return true;
    }
}