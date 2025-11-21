package com.example.loomi.api.Validation.Strategies;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.Repositories.OrderRepository;
import com.example.loomi.infrastructure.Repositories.ProductRepository;

@Component
public class DigitalProductValidator implements ProductValidator {

    private final ProductRepository productsRepository;
    private final OrderRepository orderRepository;

    public DigitalProductValidator(ProductRepository productsRepository, OrderRepository orderRepository) {
        this.productsRepository = productsRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ProductType supportedType() {
        return ProductType.DIGITAL;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos digitais
    }

    @Override
    public void validate(OrderItemEntity orderItemEntity) throws ProductValidationException {
        String requestedProductId = orderItemEntity.getProduct().getProductId();
        Integer orderRequestedAmount = orderItemEntity.getQuantity();

        var productEntity = productsRepository.findById(requestedProductId);
        Integer availableStock = productEntity.get().getLicenses();

        if (availableStock < orderRequestedAmount) {
            throw new ProductValidationException(
                    "Insufficient licenses in stock for product ID: " + requestedProductId);
        }

        // Cliente não pode comprar o mesmo produto digital mais de uma vez
        List<OrderEntity> customerOrders = orderRepository.findByCustomerId(
                orderItemEntity.getOrder().getCustomerId());

        if (customerOrders != null && !customerOrders.isEmpty()) {
            for (OrderEntity order : customerOrders) {
                for (OrderItemEntity item : order.getItems()) {
                    if (item.getProduct().getProductId().equals(requestedProductId)) {
                        throw new ProductValidationException(
                                "Customer has already purchased digital product ID: " + requestedProductId);
                    }
                }
            }
        }

    }

}
