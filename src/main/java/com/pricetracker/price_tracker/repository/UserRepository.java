package com.pricetracker.price_tracker.repository;

import com.pricetracker.price_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //used to during login weather account exist
    Optional<User> findByEmail(String email);

    //sign up to prevent from duplicate accounts
    boolean existsByEmail(String email);
}
