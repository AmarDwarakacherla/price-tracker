package com.pricetracker.price_tracker.service;

import com.pricetracker.price_tracker.dto.SignupRequest;
import com.pricetracker.price_tracker.entity.User;
import com.pricetracker.price_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    public void signup(SignupRequest request) {

        //Check whether email is already registered
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        //Check password confirmation
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException(
                    "Password and Confirm Password do not match"
            );
        }

        // Encode password
        String encodedPassword =
                passwordEncoder.encode(request.password());

        //Create User
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .contactNumber(request.contactNumber())
                .password(encodedPassword)
                .emailVerified(false)
                .build();

        userRepository.save(user);

        //Generate and send OTP
        otpService.generateOtp(request.email());
    }
}