package com.Avishkar.E_Commerce_Backend.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String userId;
    private String productId;
    private Integer quantity;
}
