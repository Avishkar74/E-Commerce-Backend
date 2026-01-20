package com.Avishkar.E_Commerce_Backend.service;

import com.Avishkar.E_Commerce_Backend.model.Product;
import com.Avishkar.E_Commerce_Backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }
}
