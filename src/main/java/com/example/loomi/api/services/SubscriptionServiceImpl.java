package com.example.loomi.api.services;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loomi.domain.SubscriptionStatus;
import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;
import com.example.loomi.infrastructure.JPAEntities.SubscriptionEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;
import com.example.loomi.infrastructure.Repositories.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final int MAX_ACTIVE = 5;

    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(CustomerRepository customerRepository,
            SubscriptionRepository subscriptionRepository) {
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public SubscriptionEntity createSubscription(String customerId, String productId, BigDecimal unitPrice) {
        CustomerEntity customer = customerRepository.findByIdForUpdate(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        long active = subscriptionRepository.countByCustomerCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE);
        if (active >= MAX_ACTIVE) {
            throw new IllegalStateException("Customer " + customerId + " already has " + MAX_ACTIVE + " active subscriptions");
        }

        SubscriptionEntity s = new SubscriptionEntity();
        s.setCustomer(customer);
        s.setProductId(productId);
        s.setStartDate(LocalDate.now());
        s.setStatus(SubscriptionStatus.ACTIVE);
        s.setUnitPrice(unitPrice);

        return subscriptionRepository.save(s);
    }

}
