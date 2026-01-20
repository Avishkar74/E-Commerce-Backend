package com.Avishkar.E_Commerce_Backend.controller;

import com.Avishkar.E_Commerce_Backend.dto.AddToCartRequest;
import com.Avishkar.E_Commerce_Backend.dto.CartItemResponse;
import com.Avishkar.E_Commerce_Backend.model.CartItem;
import com.Avishkar.E_Commerce_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor

public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/{userId}")
    public List<CartItemResponse> getUserCart(@PathVariable String userId) {
        return cartService.getUserCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public Map<String, String> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return Map.of("message", "Cart cleared successfully");
    }
}
