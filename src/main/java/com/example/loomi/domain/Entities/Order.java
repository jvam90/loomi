package com.example.loomi.domain.Entities;

import java.util.List;

import com.example.loomi.domain.Enums.OrderStatus;

public class Order {

    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private OrderStatus status;

    public Order(String orderId, String customerId, List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }    

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
