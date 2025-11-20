package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;

public interface  CustomersService {
    List<CustomerEntity> getAllCustomers();
    Optional<CustomerEntity> getCustomerById(String customerId);
}
