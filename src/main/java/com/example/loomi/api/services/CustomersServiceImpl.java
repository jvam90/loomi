package com.example.loomi.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;
import com.example.loomi.infrastructure.Repositories.CustomerRepository;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomerRepository customersRepository;

    public CustomersServiceImpl(CustomerRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customersRepository.findAll();
    }

    @Override
    public Optional<CustomerEntity> getCustomerById(String customerId) {
        var customer = customersRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

        return customer;
    }
    
}
