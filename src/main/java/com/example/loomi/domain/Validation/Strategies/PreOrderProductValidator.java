package com.example.loomi.domain.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.domain.Validation.ProductValidationException;
import com.example.loomi.domain.Validation.ProductValidator;

@Component
public class PreOrderProductValidator implements ProductValidator {

    @Override
    public ProductType supportedType() {
        return ProductType.PRE_ORDER;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos de pré-venda
    }
}
