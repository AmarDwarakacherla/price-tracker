package com.pricetracker.price_tracker.repository;

import com.pricetracker.price_tracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
