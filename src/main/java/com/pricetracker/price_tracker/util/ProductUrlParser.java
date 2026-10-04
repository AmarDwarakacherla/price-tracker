package com.pricetracker.price_tracker.util;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductUrlParser {

    private static final Pattern AMAZON_ASIN_PATTERN =
            Pattern.compile("/(?:dp|gp/product)/([A-Z0-9]{10})");

    public static Optional<String> extractAmazonAsin(String productUrl) {

        Matcher matcher = AMAZON_ASIN_PATTERN.matcher(productUrl);

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }

        return Optional.empty();
    }

    public static Optional<String> extractFlipkartPid(String productUrl) {

        try {
            URI uri = URI.create(productUrl);

            String query = uri.getQuery();

            if (query == null) {
                return Optional.empty();
            }

            for (String parameter : query.split("&")) {

                String[] keyValue = parameter.split("=", 2);

                if (keyValue.length == 2 && keyValue[0].equals("pid")) {
                    return Optional.of(keyValue[1]);
                }
            }

        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return Optional.empty();
    }
    public static Optional<String> extractCromaProductId(String productUrl) {

        Pattern pattern =
                Pattern.compile("/p/(\\d+)");

        Matcher matcher = pattern.matcher(productUrl);

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }

        return Optional.empty();
    }
}