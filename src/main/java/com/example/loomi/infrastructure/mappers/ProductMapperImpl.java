package com.example.loomi.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.domain.Entities.Product;
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
                                entity.getMetadata(),
                                entity.getLicenses(),
                                entity.getPreOrderSlots());
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
                                productEntity.getMetadata(),
                                productEntity.getLicenses(),
                                productEntity.getPreOrderSlots());

        }

}
