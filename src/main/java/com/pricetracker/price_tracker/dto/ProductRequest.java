package com.pricetracker.price_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        String productName,

        @NotBlank
        String productUrl,

        @NotNull
        BigDecimal currentPrice
) {
}
