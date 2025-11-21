package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loomi.api.Validation.ProductValidatorFactory;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;
import com.example.loomi.infrastructure.Repositories.OrderRepository;
import com.example.loomi.infrastructure.mappers.IProductMapper;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductValidatorFactory productValidatorFactory;
    private final IProductMapper productMapper;

    public OrdersServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository,
            ProductValidatorFactory productValidatorFactory, IProductMapper productMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productValidatorFactory = productValidatorFactory;
        this.productMapper = productMapper;
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

        var customerOrder = orderRepository.findByOrderIdAndCustomerId(orderId, customerId);

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

        if (orderEntity.getOrderId() != null && !orderEntity.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Order ID must be null or empty when creating a new order.");
        }

        if (orderEntity.getItems() == null || orderEntity.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        // validate each item using the registered validators
        for (OrderItemEntity orderItem : orderEntity.getItems()) {
            if (orderItem.getProduct() == null) {
                throw new IllegalArgumentException("Order item must have a product.");
            }

            // productValidatorFactory.get(orderItem.getProduct().getProductType()).validate(orderItem);

            // ensure bidirectional relationship for cascade save
            orderItem.setOrder(orderEntity);
        }

        // persist the order (cascade will save items)
        return orderRepository.save(orderEntity);
    }

}
