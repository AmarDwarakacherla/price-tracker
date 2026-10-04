package com.pricetracker.price_tracker.fetcher;

import com.pricetracker.price_tracker.dto.ProductDetails;
import com.pricetracker.price_tracker.util.ProductUrlParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AmazonPriceFetcher implements PriceFetcher {


    @Override
    public Optional<ProductDetails> fetchProduct(String productUrl) {

        Optional<String> asin =
                ProductUrlParser.extractAmazonAsin(productUrl);

        if (asin.isEmpty()) {
            return Optional.empty();
        }

        try {

            Document document = Jsoup.connect(productUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // Extract product name
            Element titleElement = document.selectFirst("#productTitle");

            if (titleElement == null) {
                System.out.println(
                        "Product name not found for ASIN: " + asin.get()
                );
                return Optional.empty();
            }

            String productName = titleElement.text().trim();

            // Extract price
            Element priceElement = document.selectFirst(
                    "#corePriceDisplay_desktop_feature_div .a-price-whole"
            );

            if (priceElement == null) {
                System.out.println(
                        "Price not found for ASIN: " + asin.get()
                );
                return Optional.empty();
            }

            String priceText = priceElement.text();

            BigDecimal price = new BigDecimal(
                    priceText.replace(",", "")
            );

            System.out.println("Amazon ASIN: " + asin.get());
            System.out.println("Amazon Product: " + productName);
            System.out.println("Amazon Price: ₹" + price);

            ProductDetails details =
                    new ProductDetails(productName, price);

            return Optional.of(details);

        } catch (Exception e) {

            System.out.println(
                    "Failed to fetch Amazon product: " + e.getMessage()
            );

            return Optional.empty();
        }
    }





//    @Override
//    public Optional<ProductDetails> fetchPrice(String productUrl) {
//
//        Optional<String> asin =
//                ProductUrlParser.extractAmazonAsin(productUrl);
//
//        if (asin.isEmpty()) {
//            return Optional.empty();
//        }
//
//        try {
//
//            Document document = Jsoup.connect(productUrl)
//                    .userAgent("Mozilla/5.0")
//                    .timeout(10000)
//                    .get();
//
//            Element priceElement = document.selectFirst(
//                    "#corePriceDisplay_desktop_feature_div .a-price-whole"
//            );
//
//            if (priceElement == null) {
//                System.out.println(
//                        "Price not found for ASIN: " + asin.get()
//                );
//                return Optional.empty();
//            }
//
//            String priceText = priceElement.text();
//
//            BigDecimal price = new BigDecimal(
//                    priceText.replace(",", "")
//            );
//
//            System.out.println("Amazon ASIN: " + asin.get());
//            System.out.println("Amazon Price: ₹" + price);
//
//            return Optional.of(price);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    "Failed to fetch Amazon price: " + e.getMessage()
//            );
//
//            return Optional.empty();
//        }
//    }
}