package com.Avishkar.E_Commerce_Backend.repository;

import com.Avishkar.E_Commerce_Backend.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByPaymentId(String paymentId);

    Optional<Payment> findByOrderId(String orderId);
}
