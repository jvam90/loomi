package com.example.loomi.api.dtos;

import java.math.BigDecimal;

public class OrderItemDto {
    private OrderDto order;
    private ProductDto product;
    private Integer quantity;
    //para snapshot de preço
    private BigDecimal unitPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(OrderDto order, ProductDto product, Integer quantity, BigDecimal unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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



}
