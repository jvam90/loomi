package com.example.loomi.infrastructure.mappers;

import com.example.loomi.api.dtos.OrderDto;
import com.example.loomi.domain.Entities.Order;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

public interface IOrderMapper {
    Order toDomainOrder(OrderEntity entity);
    OrderEntity toJPAEntityFromDto(OrderDto order);
    OrderDto toDto(OrderEntity orderEntity);
}
