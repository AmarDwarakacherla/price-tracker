package com.pricetracker.price_tracker.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;

public class JsoupPriceTest {

    public static void main(String[] args) throws Exception {

        Document document = Jsoup.parse(
                JsoupPriceTest.class
                        .getClassLoader()
                        .getResourceAsStream("test-product.html"),
                "UTF-8",
                ""
        );

        Element priceElement = document.selectFirst(".price");
        String priceText = priceElement.text();

        String cleanedPrice = priceText
                .replace("₹", "")
                .replace(",", "")
                .trim();

        BigDecimal price = new BigDecimal(cleanedPrice);

        System.out.println("Cleaned price: " + cleanedPrice);
        System.out.println("BigDecimal price: " + price);

        System.out.println("Element: " + priceElement);
        System.out.println("Text: " + priceElement.text());
    }
}