package com.example.loomi.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.domain.Metadata;
import com.example.loomi.domain.Product;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

@Component
public class ProductMapperImpl implements IProductMapper {

    @Override
    public Product toDomainProduct(ProductEntity entity) {
        return new Product(
            entity.getProductId(),
            entity.getName(),
            entity.getProductType(),
            entity.getPrice(),
            entity.getStockQuantity(),
            entity.isActive(),
           new Metadata(
                entity.getMetadata().releaseDate(),
                entity.getMetadata().preOrderSlots(),
                entity.getMetadata().licenses()
            )
        );
    }

    @Override
    public ProductDto toDto(ProductEntity productEntity) {
        return new ProductDto(
            productEntity.getProductId(),
            productEntity.getName(),
            productEntity.getProductType(),
            productEntity.getPrice(),
            productEntity.getStockQuantity(),
            productEntity.isActive(),
           new Metadata(
                productEntity.getMetadata().releaseDate(),
                productEntity.getMetadata().preOrderSlots(),
                productEntity.getMetadata().licenses()
            )
        );
    }
    
}
