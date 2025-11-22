package com.example.loomi.domain.Entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.example.loomi.domain.Enums.SubscriptionStatus;

public class Subscription {
    private Long subscriptionId;
    private Customer customer;
    private String productId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;
    private BigDecimal unitPrice;

    public Subscription() {
    }

    public Subscription(Customer customer, String productId, OffsetDateTime startDate, OffsetDateTime endDate,
            SubscriptionStatus status,
            BigDecimal unitPrice) {
        this.customer = customer;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.unitPrice = unitPrice;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
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
