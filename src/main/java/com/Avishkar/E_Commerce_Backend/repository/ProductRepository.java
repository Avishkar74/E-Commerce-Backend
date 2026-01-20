package com.Avishkar.E_Commerce_Backend.repository;

import com.Avishkar.E_Commerce_Backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
