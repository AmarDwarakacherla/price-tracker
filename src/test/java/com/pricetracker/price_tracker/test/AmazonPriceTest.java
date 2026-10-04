package com.pricetracker.price_tracker.test;

import com.pricetracker.price_tracker.util.ProductUrlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.Optional;

public class AmazonPriceTest {

    public static void main(String[] args) throws Exception {

        String url =
                "https://www.amazon.in/dp/B0GR1528P7";

        // 1. Extract ASIN from URL
        Optional<String> asinOptional =
                ProductUrlParser.extractAmazonAsin(url);

        if (asinOptional.isEmpty()) {
            System.out.println("ASIN not found");
            return;
        }

        String asin = asinOptional.get();

        System.out.println("ASIN: " + asin);

        // 2. Fetch Amazon page
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        System.out.println("Page loaded successfully");

        // 3. Find the main price using Amazon's product-price section
//        Element priceElement = document.selectFirst(
//                "#corePrice_feature_div .a-price"
//        );
//
//        // Fallback for Amazon's alternate price container
//        if (priceElement == null) {
//            priceElement = document.selectFirst(
//                    "#corePriceDisplay_desktop_feature_div .a-price"
//            );
//        }
//
//        // 4. If price isn't found
//        if (priceElement == null) {
//            System.out.println("Price not found");
//            return;
//        }
//
//        // 5. Amazon provides a clean price inside a-offscreen
//        Element offscreenPrice =
//                priceElement.selectFirst(".a-offscreen");
//
//        if (offscreenPrice == null) {
//            System.out.println("Price value not found");
//            return;
//        }
//
//        String priceText = offscreenPrice.text();
//
//        System.out.println("Raw price: " + priceText);
//
//        // 6. Convert ₹87,990.00 → 87990.00
//        String cleanedPrice = priceText
//                .replace("₹", "")
//                .replace(",", "")
//                .trim();
//
//        BigDecimal price =
//                new BigDecimal(cleanedPrice);
//
//        System.out.println("Final price: " + price);


        Element priceElement = document.selectFirst(
                "#corePriceDisplay_desktop_feature_div .a-price-whole"
        );

        if (priceElement != null) {
            String priceText = priceElement.text();

            System.out.println("Price text: " + priceText);

            BigDecimal price = new BigDecimal(
                    priceText.replace(",", "")
            );

            System.out.println("Price: " + price);
        } else {
            System.out.println("Price not found");
        }

    }
}