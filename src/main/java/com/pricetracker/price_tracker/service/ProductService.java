package com.pricetracker.price_tracker.service;

import com.pricetracker.price_tracker.dto.ProductDetails;
import com.pricetracker.price_tracker.dto.ProductRequest;
import com.pricetracker.price_tracker.entity.PriceHistory;
import com.pricetracker.price_tracker.entity.Product;
import com.pricetracker.price_tracker.fetcher.PriceFetcher;
import com.pricetracker.price_tracker.repository.PriceHistoryRepository;
import com.pricetracker.price_tracker.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    private final PriceHistoryRepository priceHistoryRepository;
    private final PriceFetcher priceFetcher;

    @Transactional
    public Product createProduct(ProductRequest request) {

        LocalDateTime now = LocalDateTime.now();

        ProductDetails details = priceFetcher
                .fetchProduct(request.productUrl())
                .orElseThrow(() ->
                        new RuntimeException("Could not fetch product details"));

        Product product = Product.builder()
                .productName(details.productName())
                .productUrl(request.productUrl())
                .currentPrice(details.price())
                .previousPrice(details.price())
                .createdAt(now)
                .lastCheckedAt(now)
                .build();

        Product savedProduct = productRepository.save(product);

        PriceHistory history = PriceHistory.builder()
                .price(details.price())
                .checkedAt(now)
                .product(savedProduct)
                .build();
        priceHistoryRepository.save(history);
        savedProduct.getPriceHistory().add(history);
        return savedProduct;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
    }

    @Transactional
    public Product updateProduct(Long id, BigDecimal newPrice){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found..."));

        //no price changed
        if(product.getCurrentPrice().compareTo(newPrice) == 0){
            return product;
        }
        LocalDateTime now = LocalDateTime.now();


        //price changed
        product.setPreviousPrice(product.getCurrentPrice());
        product.setCurrentPrice(newPrice);
        product.setLastCheckedAt(now);

        //@Transactional - Hibernate tracks that change through dirty checking. - So save() isn't required for the update.
//        productRepository.save(product);

        PriceHistory history = PriceHistory.builder()
                .price(newPrice)
                .checkedAt(now)
                .product(product)
                .build();
        priceHistoryRepository.save(history);

        return product;
    }

    @Transactional
    public Product checkPrice(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
        Optional<ProductDetails> latestPrice = priceFetcher.fetchProduct(product.getProductUrl());
        if(latestPrice.isEmpty()){
            throw new RuntimeException("Could not fetch latest price/Product out of stock");
        }
        BigDecimal newPrice = latestPrice.get().price();
//        System.out.println("Old price: ₹" + product.getCurrentPrice());
//        System.out.println("New price: ₹" + newPrice);

        if (newPrice.compareTo(product.getCurrentPrice()) < 0) {

            LocalDateTime now = LocalDateTime.now();
            product.setPreviousPrice(product.getCurrentPrice());
            product.setCurrentPrice(newPrice);
            product.setLastCheckedAt(now);

            PriceHistory history = PriceHistory.builder()
                    .price(newPrice)
                    .checkedAt(now)
                    .product(product)
                    .build();

            priceHistoryRepository.save(history);

            System.out.println("🔥 PRICE DROPPED!");
            System.out.println("Old price: ₹" + product.getPreviousPrice());
            System.out.println("New price: ₹" + newPrice);
        } else {
            product.setLastCheckedAt(LocalDateTime.now());
            System.out.println("No price drop.");
        }
        return product;
    }
}
