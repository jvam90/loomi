package com.example.loomi.api.Validation;

import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;

public interface ProductValidator {
    ProductType supportedType();

    void validate(Product product) throws ProductValidationException;

    void validate(OrderItemEntity orderItemEntity) throws ProductValidationException;
}
