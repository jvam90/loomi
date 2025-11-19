package com.example.loomi.domain;

import java.util.List;

public class Order {

    private String orderId;
    private String customerId;
    private List<Item> items;
    private OrderStatus status;

    public Order(String orderId, String customerId, List<Item> items, OrderStatus status) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }    

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
