package com.example.loomi.api.dtos;

import java.math.BigDecimal;
import java.util.Map;

import com.example.loomi.domain.Enums.ProductType;

public class ProductDto {
    private String productId;
    private String name;
    private ProductType productType;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean active = true;
    private Map<String, Object> metadata;
    private Integer licenses;
    private Integer preOrderSlots;

    public ProductDto() {
    }

    public ProductDto(String productId, String name, ProductType productType, BigDecimal price, Integer stockQuantity,
            boolean active,
            Map<String, Object> metadata, Integer licenses, Integer preOrderSlots) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.active = active;
        this.metadata = metadata;
        this.licenses = licenses;
        this.preOrderSlots = preOrderSlots;
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

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Integer getLicenses() {
        return licenses;
    }

    public void setLicenses(Integer licenses) {
        this.licenses = licenses;
    }

    public Integer getPreOrderSlots() {
        return preOrderSlots;
    }

    public void setPreOrderSlots(Integer preOrderSlots) {
        this.preOrderSlots = preOrderSlots;
    }
}
