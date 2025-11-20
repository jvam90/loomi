package com.example.loomi.api.dtos;

import java.time.OffsetDateTime;
import java.util.List;

public class CustomerDto {
    private String name;
    private String email;
    private OffsetDateTime createdAt;
    private List<SubscriptionDto> subscriptions;

    public CustomerDto() {
    }

    public CustomerDto(String name, String email, OffsetDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SubscriptionDto> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
