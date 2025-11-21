package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

public interface OrdersService {
    List<OrderEntity> getAllOrdersByCustomerId(String customerId);
    Optional<OrderEntity> findByIdAndCustomerId(String orderId, String customerId);
    OrderEntity createOrder(OrderEntity orderEntity);
}
