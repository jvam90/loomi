package com.example.loomi.infrastructure.mappers;

import com.example.loomi.api.dtos.OrderDto;
import com.example.loomi.domain.Order;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

public interface IOrderMapper {
    Order toDomainOrder(OrderEntity entity);
    OrderEntity toJPAEntity(Order order);
    OrderDto toDto(OrderEntity orderEntity);
}
