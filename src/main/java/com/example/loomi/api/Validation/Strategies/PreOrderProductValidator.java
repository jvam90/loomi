package com.example.loomi.api.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.Repositories.ProductRepository;

@Component
public class PreOrderProductValidator implements ProductValidator {

    private final ProductRepository productsRepository;

    public PreOrderProductValidator(ProductRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public ProductType supportedType() {
        return ProductType.PRE_ORDER;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos de pré-venda
    }

    @Override
    public void validate(OrderItemEntity orderItemEntity) throws ProductValidationException {
        String requestedProductId = orderItemEntity.getProduct().getProductId();
        Integer orderRequestedAmount = orderItemEntity.getQuantity();

        var productEntity = productsRepository.findById(requestedProductId);
        Integer availableStock = productEntity.get().getPreOrderSlots();

        if (availableStock < orderRequestedAmount) {
            throw new ProductValidationException("Insufficient pre-order slots for product ID: " + requestedProductId);
        }
    }

}
