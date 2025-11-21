package com.example.loomi.api.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        return customer;
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) {
        if (customer.getCustomerId() != null && !customer.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Customer ID must be null or empty when creating a new customer");
        }

        if (!customersRepository.findByEmail(customer.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Customer with Email " + customer.getEmail() + " already exists.");
        }

        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(OffsetDateTime.now());
        customer.setSubscriptions(new ArrayList<>());
        return customersRepository.save(customer);
    }

    @Override
    public Optional<CustomerEntity> getCustomerByEmail(String email) {
        var customer = customersRepository.findByEmail(email);

        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with email: " + email);
        }

        return customer;
    }

}
