package com.example.loomi.domain;

import java.util.List;

public class Order {

    private String orderId;
    private String customerId;
    private List<Item> items;

    public Order(String orderId, String customerId, List<Item> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
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

    
    
}
