package com.pricetracker.price_tracker.controller;

import com.pricetracker.price_tracker.dto.ProductRequest;
import com.pricetracker.price_tracker.entity.Product;
import com.pricetracker.price_tracker.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestParam BigDecimal newPrice){
        return ResponseEntity.ok(productService.updateProduct(id, newPrice));
    }

    @PostMapping("/{id}/check-price")
    public ResponseEntity<Product> checkPrice(@PathVariable Long id) {
        return ResponseEntity.ok(productService.checkPrice(id));
    }



}

