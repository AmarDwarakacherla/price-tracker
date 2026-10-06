package com.pricetracker.price_tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Price Tracker - Email Verification OTP");

        message.setText(
                "Your Price Tracker verification OTP is: " + otp +
                        "\n\nThis OTP is valid for 5 minutes." +
                        "\n\nIf you did not request this, please ignore this email."
        );

        mailSender.send(message);
    }
}