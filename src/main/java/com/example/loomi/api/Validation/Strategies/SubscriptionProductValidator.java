package com.example.loomi.api.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.domain.Enums.SubscriptionStatus;
import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;
import com.example.loomi.infrastructure.Repositories.SubscriptionRepository;

@Component
public class SubscriptionProductValidator implements ProductValidator {

    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionProductValidator(CustomerRepository customerRepository,
            SubscriptionRepository subscriptionRepository) {
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public ProductType supportedType() {
        return ProductType.SUBSCRIPTION;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos de assinatura
    }

    @Override
    public void validate(OrderItemEntity orderItemEntity) throws ProductValidationException {
        // Um cliente não pode ter 5 assinaturas ativas ao mesmo tempo
        if (subscriptionRepository.countByCustomerCustomerIdAndStatus(
                orderItemEntity.getOrder().getCustomerId(), SubscriptionStatus.ACTIVE) >= 5) {
            throw new ProductValidationException("Customer ID" + orderItemEntity.getOrder().getCustomerId()
                    + " has reached the maximum number of active subscriptions.");
        }

        // Um cliente não pode ter mais de uma assinatura do mesmo produto ativa ao
        // mesmo tempo
        CustomerEntity customer = customerRepository.findById(orderItemEntity.getOrder().getCustomerId()).get();
        customer.getSubscriptions().stream()
                .filter(sub -> sub.getProductId().equals(orderItemEntity.getProduct().getProductId())
                        && sub.getStatus() == SubscriptionStatus.ACTIVE)
                .findAny()
                .ifPresent(sub -> {
                    throw new ProductValidationException("Customer ID" + orderItemEntity.getOrder().getCustomerId()
                            + " already has an active subscription for Product ID"
                            + orderItemEntity.getProduct().getProductId() + ".");
                });

        // Um cliente não pode ter uma assinatura gratis e uma paga do mesmo produto ao
        // mesmo tempo
        customer.getSubscriptions().stream()
                .filter(sub -> sub.getProductId().equals(orderItemEntity.getProduct().getProductId()))
                .filter(sub -> sub.getStatus() == SubscriptionStatus.ACTIVE)
                .filter(sub -> sub.getUnitPrice().doubleValue() == 0.0
                        || orderItemEntity.getUnitPrice().doubleValue() == 0.0)
                .findAny()
                .ifPresent(sub -> {
                    throw new ProductValidationException("Customer ID" + orderItemEntity.getOrder().getCustomerId()
                            + " cannot have both free and paid subscriptions for Product ID"
                            + orderItemEntity.getProduct().getProductId() + " at the same time.");
                });
    }

}
