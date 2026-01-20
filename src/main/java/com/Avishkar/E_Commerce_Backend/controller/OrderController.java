package com.Avishkar.E_Commerce_Backend.controller;

import com.Avishkar.E_Commerce_Backend.dto.CreateOrderRequest;
import com.Avishkar.E_Commerce_Backend.dto.OrderResponse;
import com.Avishkar.E_Commerce_Backend.model.Order;
import com.Avishkar.E_Commerce_Backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request.getUserId());
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PostMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }

}
