package com.example.loomi.api.Validation;

import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;

public interface ProductValidator {
    ProductType supportedType();

    void validate(Product product) throws ProductValidationException;
}
