package com.example.loomi.api.dtos;

import java.math.BigDecimal;

import com.example.loomi.domain.Metadata;
import com.example.loomi.domain.ProductType;

public class ProductDto {
    private String productId;
    private String name;
    private ProductType productType;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean active = true;
    private Metadata metadata;

    public ProductDto() {
    }

    public ProductDto(String productId, String name, ProductType productType, BigDecimal price, Integer stockQuantity,
            boolean active,
            Metadata metadata) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.active = active;
        this.metadata = metadata;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
