package com.example.loomi.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loomi.api.dtos.OrderDto;
import com.example.loomi.api.services.OrdersService;
import com.example.loomi.infrastructure.mappers.IOrderMapper;



@RestController
@RequestMapping(path = "/api/orders")
public class OrdersController {
    
    private final OrdersService ordersService;
    private final IOrderMapper orderMapper;

    public OrdersController(OrdersService ordersService, IOrderMapper orderMapper) {
        this.ordersService = ordersService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{customerId}")
    public List<OrderDto> getMethodName(@PathVariable("customerId") String customerId) {
        return ordersService.getAllOrdersByCustomerId(customerId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

}
