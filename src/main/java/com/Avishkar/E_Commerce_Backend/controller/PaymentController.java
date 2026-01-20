package com.Avishkar.E_Commerce_Backend.controller;

import com.Avishkar.E_Commerce_Backend.dto.PaymentRequest;
import com.Avishkar.E_Commerce_Backend.model.Payment;
import com.Avishkar.E_Commerce_Backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest request) throws Exception {
        return paymentService.createPayment(
                request.getOrderId(),
                request.getAmount());
    }

}
