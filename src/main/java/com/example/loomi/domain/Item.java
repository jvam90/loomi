package com.example.loomi.domain;

public class Item {
    private String productId;
    private Integer quantity;
    private Metadata metadata;

    public Item(String productId, Integer quantity, Metadata metadata) {
        this.productId = productId;
        this.quantity = quantity;
        this.metadata = metadata;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    
    
}
