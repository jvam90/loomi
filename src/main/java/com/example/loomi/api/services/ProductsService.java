package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

public interface ProductsService {
    List<ProductEntity> getAllProducts();

    Optional<ProductEntity> getProductById(String productId);

    Optional<ProductEntity> findByProductIdForUpdate(String productId);
}
