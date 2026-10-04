package com.pricetracker.price_tracker.fetcher;

import com.pricetracker.price_tracker.dto.ProductDetails;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceFetcher {
//    Optional<BigDecimal> fetchPrice(String productUrl);
    Optional<ProductDetails> fetchProduct(String productUrl);
}
