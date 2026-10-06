package com.pricetracker.price_tracker.repository;

import com.pricetracker.price_tracker.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository
        extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmailAndOtp(
            String email,
            String otp
    );
}