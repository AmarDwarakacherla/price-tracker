package com.pricetracker.price_tracker.repository;

import com.pricetracker.price_tracker.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}
