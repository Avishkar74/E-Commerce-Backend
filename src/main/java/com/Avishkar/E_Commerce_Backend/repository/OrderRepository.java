package com.Avishkar.E_Commerce_Backend.repository;

import com.Avishkar.E_Commerce_Backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
