package com.example.loomi.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loomi.api.dtos.CustomerDto;
import com.example.loomi.api.services.CustomersService;
import com.example.loomi.infrastructure.mappers.ICustomerMapper;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomersService customerService;
    private final ICustomerMapper customerMapper;

    public CustomersController(CustomersService customersService, ICustomerMapper customerMapper) {
        this.customerService = customersService;
        this.customerMapper = customerMapper;
    }

    @GetMapping()
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream().map(customerMapper::toDto).toList();
    }

    @GetMapping("/{customerId}")
    public Optional<CustomerDto> getCustomerById(@PathVariable("customerId") String customerId) {
        return customerService.getCustomerById(customerId).map(customerMapper::toDto);
    }

    @PostMapping()
    public CustomerDto postMethodName(@RequestBody CustomerDto entity) {
        var customerEntity = customerService.createCustomer(customerMapper.toJPAEntityFromDto(entity));
        return customerMapper.toDto(customerEntity);
    }

    @GetMapping("/email/{customerEmail}")
    public Optional<CustomerDto> getCustomerByEmail(@PathVariable("customerEmail") String customerEmail) {
        return customerService.getCustomerByEmail(customerEmail).map(customerMapper::toDto);
    }

}
