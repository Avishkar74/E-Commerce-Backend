package com.Avishkar.E_Commerce_Backend.service;

import com.Avishkar.E_Commerce_Backend.dto.OrderResponse;
import com.Avishkar.E_Commerce_Backend.model.*;
import com.Avishkar.E_Commerce_Backend.model.enums.OrderStatus;
import com.Avishkar.E_Commerce_Backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    public Order createOrder(String userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is Empty");
        }

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(Instant.now());

        orderRepository.save(order);

        double totalAmount = 0;
        for (CartItem cartItem : cartItems) {

            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // reduce stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            totalAmount += product.getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        cartRepository.deleteByUserId(userId);
        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Only CREATED orders can be cancelled");
        }

        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        for (OrderItem item : items) {
            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow();

            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public OrderResponse getOrderDetails(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        Payment payment = paymentRepository.findByOrderId(orderId).orElse(null);

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());

        // map items
        List<OrderResponse.OrderItemResponse> items = orderItems.stream().map(item -> {

            OrderResponse.OrderItemResponse r = new OrderResponse.OrderItemResponse();

            r.setProductId(item.getProductId());
            r.setQuantity(item.getQuantity());
            r.setPrice(item.getPrice());

            return r;
        }).toList();

        response.setItems(items);

        // map payment
        if (payment != null) {
            OrderResponse.PaymentResponse pr = new OrderResponse.PaymentResponse();

            pr.setId(payment.getId());
            pr.setStatus(payment.getStatus().name());
            pr.setAmount(payment.getAmount());

            response.setPayment(pr);
        }

        return response;
    }

}
