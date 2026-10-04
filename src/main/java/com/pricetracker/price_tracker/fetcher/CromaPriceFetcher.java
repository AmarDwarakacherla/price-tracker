package com.pricetracker.price_tracker.fetcher;

import com.pricetracker.price_tracker.dto.ProductDetails;

import java.math.BigDecimal;
import java.util.Optional;

public class CromaPriceFetcher implements PriceFetcher {

    @Override
    public Optional<ProductDetails> fetchProduct(String productUrl) {
        return Optional.empty();
    }
}
