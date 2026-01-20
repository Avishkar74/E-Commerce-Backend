package com.Avishkar.E_Commerce_Backend.model;

import com.Avishkar.E_Commerce_Backend.model.enums.PaymentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "payments")
@Data
public class Payment {
    @Id
    private String id;

    private String orderId;
    private Double amount;
    private PaymentStatus status;
    private String paymentId;
    private Instant createdAt;
}
