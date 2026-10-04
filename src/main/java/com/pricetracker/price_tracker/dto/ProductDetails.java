package com.pricetracker.price_tracker.dto;

import java.math.BigDecimal;

public record ProductDetails(String productName,
                             BigDecimal price){
}
