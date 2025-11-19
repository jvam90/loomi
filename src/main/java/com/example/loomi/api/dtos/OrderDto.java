package com.example.loomi.api.dtos;

import java.util.List;

import com.example.loomi.domain.OrderStatus;

public class OrderDto {
    private String customerId;
    private List<OrderItemDto> items;
    private OrderStatus status;

    public OrderDto() {
    }

    public OrderDto(String customerId, List<OrderItemDto> items, OrderStatus status) {
        this.customerId = customerId;
        this.items = items;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }



}
