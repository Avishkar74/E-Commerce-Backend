package com.Avishkar.E_Commerce_Backend.repository;

import com.Avishkar.E_Commerce_Backend.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
    List<OrderItem> findByOrderId(String orderId);
}
