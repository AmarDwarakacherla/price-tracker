package com.pricetracker.price_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-amazon")
    public String testAmazon() {

        return "Amazon fetcher bean is working";
    }
}
