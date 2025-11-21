package com.example.loomi.domain.Entities;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    // para snapshot de preço
    private BigDecimal unitPrice;
    private UUID activationKey;

    public OrderItem() {
    }

    public OrderItem(Product product, Integer quantity, BigDecimal unitPrice, UUID activationKey) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.activationKey = activationKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public UUID getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(UUID activationKey) {
        this.activationKey = activationKey;
    }

}