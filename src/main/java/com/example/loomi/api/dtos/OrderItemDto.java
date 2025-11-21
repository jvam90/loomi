package com.example.loomi.api.dtos;

import java.math.BigDecimal;
import java.util.Map;

public class OrderItemDto {
    private OrderDto order;
    private String productId;
    private ProductDto product;
    private Integer quantity;
    // para snapshot de preço
    private BigDecimal unitPrice;
    private String activationKey;
    private Map<String, Object> metadata;

    public OrderItemDto() {
    }

    public OrderItemDto(OrderDto order, ProductDto product, Integer quantity, BigDecimal unitPrice,
            String activationKey, Map<String, Object> metadata) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.activationKey = activationKey;
        this.metadata = metadata;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
