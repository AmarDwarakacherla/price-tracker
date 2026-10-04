package com.pricetracker.price_tracker;

import com.pricetracker.price_tracker.dto.ProductDetails;
import com.pricetracker.price_tracker.fetcher.AmazonPriceFetcher;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

//        String url = "https://www.amazon.in/Apple-2026-MacBook-Laptop-chip/dp/B0GR1528P7/261-6717412-7057521?pd_rd_w=sqXuL&content-id=amzn1.sym.d1406b44-aa69-47e4-9270-f613e12d52dc&pf_rd_p=d1406b44-aa69-47e4-9270-f613e12d52dc&pf_rd_r=WPXKG6XCTWBZEDKN1Z61&pd_rd_wg=fdpxu&pd_rd_r=b7ab4753-e87b-474c-88ef-d7fb039fab2a&pd_rd_i=B0GR1528P7&th=1";
        String url = "https://www.amazon.in/Apple-Mac-mini-M6-Chip/dp/B0HGGMT747/261-6717412-7057521?pd_rd_w=NGHQI&content-id=amzn1.sym.d1406b44-aa69-47e4-9270-f613e12d52dc&pf_rd_p=d1406b44-aa69-47e4-9270-f613e12d52dc&pf_rd_r=DGGWHN6QDFYW2MVNYB84&pd_rd_wg=ceGyf&pd_rd_r=a24ea134-6f0e-42f7-b6fb-8cc60ad376a9&pd_rd_i=B0HGGMT747&th=1";


        AmazonPriceFetcher fetcher = new AmazonPriceFetcher();

        Optional<ProductDetails> price = fetcher.fetchProduct(url);

        if (price.isPresent()) {
            System.out.println("Final price: ₹" + price.get());
        } else {
            System.out.println("Price could not be fetched");
        }
    }
}