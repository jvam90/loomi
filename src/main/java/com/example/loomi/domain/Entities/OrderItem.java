package com.example.loomi.domain.Entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

public class OrderItem {

    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    // para snapshot de preço
    private BigDecimal unitPrice;
    private String activationKey;
    private Map<String, Object> metadata;
    private String deliveryTime;
    private OffsetDateTime firstBillingDate;

    public OrderItem() {
    }

    public OrderItem(Product product, Integer quantity, BigDecimal unitPrice, String activationKey,
            Map<String, Object> metadata) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.activationKey = activationKey;
        this.metadata = metadata;
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

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public OffsetDateTime getFirstBillingDate() {
        return firstBillingDate;
    }

    public void setFirstBillingDate(OffsetDateTime firstBillingDate) {
        this.firstBillingDate = firstBillingDate;
    }

}