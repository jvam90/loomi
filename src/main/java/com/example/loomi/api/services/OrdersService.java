package com.example.loomi.api.services;

import java.util.List;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

public interface OrdersService {
    List<OrderEntity> getAllOrdersByCustomerId(String customerId);
}
