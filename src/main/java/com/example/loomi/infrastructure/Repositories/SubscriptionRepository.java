package com.example.loomi.infrastructure.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loomi.infrastructure.JPAEntities.SubscriptionEntity;
import com.example.loomi.domain.SubscriptionStatus;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    long countByCustomerCustomerIdAndStatus(String customerId, SubscriptionStatus status);
}
