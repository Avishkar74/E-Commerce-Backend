package com.Avishkar.E_Commerce_Backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private String id;
    private String userId;
    private Double totalAmount;
    private String status;
    private List<OrderItemResponse> items;
    private PaymentResponse payment;

    @Data
    public static class OrderItemResponse {
        private String productId;
        private Integer quantity;
        private Double price;
    }

    @Data
    public static class PaymentResponse {
        private String id;
        private String status;
        private Double amount;
    }
}
