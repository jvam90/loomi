package com.example.loomi.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;

public class Subscription {
    private Long subscriptionId;
    private CustomerEntity customer;
    private String productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;
    private BigDecimal unitPrice;

    public Subscription() {
    }

    public Subscription(CustomerEntity customer, LocalDate endDate, String productId, LocalDate startDate, BigDecimal unitPrice) {
        this.customer = customer;
        this.endDate = endDate;
        this.productId = productId;
        this.startDate = startDate;
        this.unitPrice = unitPrice;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    
}
