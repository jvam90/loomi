package com.example.loomi.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.api.services.ProductsService;
import com.example.loomi.infrastructure.mappers.IProductMapper;



@RestController
@RequestMapping(path = "/api/products")
public class ProductsController {

    private final IProductMapper productMapper;
    private final ProductsService productsService;


    public ProductsController(IProductMapper productMapper, ProductsService productsService) {
        this.productMapper = productMapper;
        this.productsService = productsService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productsService.getAllProducts().stream()
            .map(productMapper::toDto)
            .toList();
    }

    @GetMapping("/{productId}")
    public Optional<ProductDto> getProductById(@PathVariable("productId") String productId) {
       return productsService.getProductById(productId)
            .map(productMapper::toDto);            
    }
    
    
    
}
