package com.example.loomi.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.Repositories.OrderRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;

    public OrdersServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
}
