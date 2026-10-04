package com.pricetracker.price_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;


    @Column(columnDefinition = "TEXT")
    private String productUrl;

    private BigDecimal currentPrice;

    private BigDecimal previousPrice;

    private LocalDateTime lastCheckedAt;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PriceHistory> priceHistory = new ArrayList<>();

}
