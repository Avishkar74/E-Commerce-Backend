package com.Avishkar.E_Commerce_Backend.repository;

import com.Avishkar.E_Commerce_Backend.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<CartItem, String> {
    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);
}
