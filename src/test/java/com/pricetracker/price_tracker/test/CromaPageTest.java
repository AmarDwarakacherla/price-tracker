package com.pricetracker.price_tracker.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CromaPageTest {

    public static void main(String[] args) throws Exception {

        String url = "https://www.croma.com/apple-macbook-air-13-6-inch-m5-16gb-512gb-macos-tahoe-starlight-/p/324330";

        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        System.out.println("Title: " + document.title());

        System.out.println("HTML length: " + document.html().length());

        System.out.println("Contains ₹: " + document.html().contains("₹"));

        System.out.println("Contains price text: "
                + document.text().toLowerCase().contains("price"));
    }
}