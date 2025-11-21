package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;
import com.example.loomi.infrastructure.Repositories.OrderRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrdersServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomerId(String customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<OrderEntity> findByIdAndCustomerId(String orderId, String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        var customerOrder = orderRepository.findByIdAndCustomerId(orderId, customerId);

        if (customerOrder.isEmpty()) {
            throw new IllegalArgumentException(
                    "Order with ID " + orderId + " for Customer ID " + customerId + " does not exist.");
        }

        return customerOrder;
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        if (!customerRepository.existsById(orderEntity.getCustomerId())) {
            throw new IllegalArgumentException("Customer with ID " + orderEntity.getCustomerId() + " does not exist.");
        }

        

        return null;
    }

}
