package com.example.loomi.api.services;

import java.math.BigDecimal;

import com.example.loomi.infrastructure.JPAEntities.SubscriptionEntity;

public interface SubscriptionService {
    SubscriptionEntity createSubscription(String customerId, String productId, BigDecimal unitPrice);
}
