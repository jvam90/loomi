package com.example.loomi.api.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;

@Component
public class CorporateProductValidator implements ProductValidator {

    @Override
    public ProductType supportedType() {
        return ProductType.CORPORATE;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos corporativos
    }

    @Override
    public void validate(OrderItemEntity orderItemEntity) throws ProductValidationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }
}
