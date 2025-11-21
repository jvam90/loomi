package com.example.loomi.api.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

@Component
public class DigitalProductValidator implements ProductValidator {

    @Override
    public ProductType supportedType() {
        return ProductType.DIGITAL;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos digitais
    }

}
