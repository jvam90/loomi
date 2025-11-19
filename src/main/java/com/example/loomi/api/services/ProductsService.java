package com.example.loomi.api.services;

import java.util.List;

import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

public interface ProductsService {
    List<ProductEntity> getAllProducts();
}
