package com.Avishkar.E_Commerce_Backend.service;

import com.Avishkar.E_Commerce_Backend.dto.AddToCartRequest;
import com.Avishkar.E_Commerce_Backend.dto.CartItemResponse;
import com.Avishkar.E_Commerce_Backend.model.CartItem;
import com.Avishkar.E_Commerce_Backend.model.Product;
import com.Avishkar.E_Commerce_Backend.repository.CartRepository;
import com.Avishkar.E_Commerce_Backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItem addToCart(AddToCartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID().toString());
        cartItem.setUserId(request.getUserId());
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());

        return cartRepository.save(cartItem);
    }

    public List<CartItemResponse> getUserCart(String userId) {

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        return cartItems.stream().map(cartItem -> {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow();

            CartItemResponse response = new CartItemResponse();
            response.setId(cartItem.getId());
            response.setProductId(cartItem.getProductId());
            response.setQuantity(cartItem.getQuantity());

            CartItemResponse.ProductSummary summary = new CartItemResponse.ProductSummary();

            summary.setId(product.getId());
            summary.setName(product.getName());
            summary.setPrice(product.getPrice());

            response.setProduct(summary);

            return response;

        }).toList();
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
