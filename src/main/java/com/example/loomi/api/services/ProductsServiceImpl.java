package com.example.loomi.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loomi.infrastructure.JPAEntities.ProductEntity;
import com.example.loomi.infrastructure.Repositories.ProductRepository;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    
}
