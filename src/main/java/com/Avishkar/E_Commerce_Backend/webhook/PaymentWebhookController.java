package com.Avishkar.E_Commerce_Backend.webhook;

import com.Avishkar.E_Commerce_Backend.model.Order;
import com.Avishkar.E_Commerce_Backend.model.Payment;
import com.Avishkar.E_Commerce_Backend.model.enums.OrderStatus;
import com.Avishkar.E_Commerce_Backend.model.enums.PaymentStatus;
import com.Avishkar.E_Commerce_Backend.repository.OrderRepository;
import com.Avishkar.E_Commerce_Backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/payment")
    public void handlePaymentWebhook(@RequestBody String payload) {

        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");

        if ("payment.captured".equals(event)) {

            String razorpayOrderId = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity")
                    .getString("order_id");

            Optional<Payment> paymentOpt = paymentRepository.findByPaymentId(razorpayOrderId);

            if (paymentOpt.isPresent()) {

                Payment payment = paymentOpt.get();
                payment.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(payment);

                Order order = orderRepository.findById(payment.getOrderId()).get();
                order.setStatus(OrderStatus.PAID);
                orderRepository.save(order);
            }
        }
    }
}
