package com.Avishkar.E_Commerce_Backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart_items")
@Data
public class CartItem {
    @Id
    private String id;

    private String userId;
    private String productId;
    private Integer quantity;
}
