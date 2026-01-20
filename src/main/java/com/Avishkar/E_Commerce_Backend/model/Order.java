package com.Avishkar.E_Commerce_Backend.model;

import com.Avishkar.E_Commerce_Backend.model.enums.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "orders")
@Data
public class Order {
    @Id
    private String id;

    private String userId;
    private Double totalAmount;
    private OrderStatus status;
    private Instant createdAt;
}
