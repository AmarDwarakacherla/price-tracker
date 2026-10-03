package com.pricetracker.price_tracker.service;

import com.pricetracker.price_tracker.dto.ProductRequest;
import com.pricetracker.price_tracker.entity.PriceHistory;
import com.pricetracker.price_tracker.entity.Product;
import com.pricetracker.price_tracker.repository.PriceHistoryRepository;
import com.pricetracker.price_tracker.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    private final PriceHistoryRepository priceHistoryRepository;

    public Product createProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.productName())
                .productUrl(request.productUrl())
                .currentPrice(request.currentPrice())
                .previousPrice(request.currentPrice())
                .createdAt(LocalDateTime.now())
                .lastCheckedAt(LocalDateTime.now())
                .build();


        Product product1 =  productRepository.save(product);

        PriceHistory history = PriceHistory.builder()
                .price(request.currentPrice())
                .checkedAt(LocalDateTime.now())
                .product(product)
                .build();
        priceHistoryRepository.save(history);

        product.getPriceHistory().add(history);

        return product1;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
    }
}
