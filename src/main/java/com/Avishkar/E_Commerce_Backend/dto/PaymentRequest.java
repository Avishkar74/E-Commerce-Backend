package com.Avishkar.E_Commerce_Backend.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private Double amount;
}