package com.Avishkar.E_Commerce_Backend.service;

import com.Avishkar.E_Commerce_Backend.model.Order;
import com.Avishkar.E_Commerce_Backend.model.Payment;
import com.Avishkar.E_Commerce_Backend.model.enums.OrderStatus;
import com.Avishkar.E_Commerce_Backend.model.enums.PaymentStatus;
import com.Avishkar.E_Commerce_Backend.repository.OrderRepository;
import com.Avishkar.E_Commerce_Backend.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment createPayment(String orderId, Double amount) throws Exception {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order already paid or invalid");
        }

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // paise
        options.put("currency", "INR");
        options.put("receipt", orderId);

        // ✅ IMPORTANT FIX: fully qualified Razorpay Order
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentId(razorpayOrder.get("id"));
        payment.setCreatedAt(Instant.now());

        return paymentRepository.save(payment);
    }
}