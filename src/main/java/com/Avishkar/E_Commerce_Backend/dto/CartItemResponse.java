package com.Avishkar.E_Commerce_Backend.dto;

import lombok.Data;

@Data
public class CartItemResponse {

    private String id;
    private String productId;
    private Integer quantity;
    private ProductSummary product;

    @Data
    public static class ProductSummary {
        private String id;
        private String name;
        private Double price;
    }
}
