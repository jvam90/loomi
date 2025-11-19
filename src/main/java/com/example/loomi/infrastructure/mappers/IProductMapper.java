package com.example.loomi.infrastructure.mappers;

import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.domain.Product;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

public interface IProductMapper {
    Product toDomainProduct(ProductEntity entity);
    ProductEntity toJPAEntity(Product product);
    ProductDto toDto(ProductEntity productEntity);
}
