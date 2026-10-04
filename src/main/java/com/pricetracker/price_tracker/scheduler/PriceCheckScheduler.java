package com.pricetracker.price_tracker.scheduler;

import com.pricetracker.price_tracker.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCheckScheduler {

    private final ProductService productService;

//    @PostConstruct
//    public void testSchedulerBean() {
//        System.out.println("🔥 PriceCheckScheduler bean CREATED");
//    }

//    @Scheduled(fixedRate = 5000)
//    public void checkPrices() {
//        System.out.println("🔥🔥 SCHEDULER IS RUNNING 🔥🔥");
//    }
    @Scheduled(fixedRate = 60000)
    public void checkPrices() {
        System.out.println("🔄 Scheduled price check started...");
        productService.getAllProducts()
                .forEach(product -> {
                    try {
                        System.out.println("Checking product: " + product.getProductName());
                        productService.checkPrice(product.getId());
                    } catch (Exception e) {
                        System.out.println("❌ Failed to check product: " + product.getId()+ " - "+ e.getMessage());
                    }
                });
    }
}