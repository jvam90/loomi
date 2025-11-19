package com.example.loomi.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.loomi.api.dtos.OrderDto;
import com.example.loomi.api.dtos.OrderItemDto;
import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.domain.Order;
import com.example.loomi.domain.OrderItem;
import com.example.loomi.domain.OrderStatus;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

@Component
public class OrderMapperImpl implements IOrderMapper {

    private final IProductMapper productMapper;

    public OrderMapperImpl(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Order toDomainOrder(OrderEntity entity) {
        List<OrderItem> items = new ArrayList<>();
        Order order = new Order(entity.getOrderId(), entity.getCustomerId(), items, entity.getStatus());

        if (entity.getItems() != null) {
            for (OrderItemEntity ie : entity.getItems()) {
                var productEntity = ie.getProduct();
                var product = productMapper.toDomainProduct(productEntity);
                OrderItem oi = new OrderItem(product, ie.getQuantity(), ie.getUnitPrice());
                oi.setId(ie.getId());
                oi.setOrder(order);
                items.add(oi);
            }
        }

        return order;
    }

    @Override
    public OrderEntity toJPAEntity(Order order) {
        OrderEntity entity = new OrderEntity(order.getOrderId(), order.getCustomerId());
        entity.setStatus(order.getStatus() != null ? order.getStatus() : OrderStatus.PENDING);

        if (order.getItems() != null) {
            for (OrderItem oi : order.getItems()) {
                OrderItemEntity ie = new OrderItemEntity();
                ie.setId(oi.getId());
                // create minimal ProductEntity reference (only id) to avoid depending on full product mapper
                ProductEntity pe = new ProductEntity();
                if (oi.getProduct() != null) {
                    pe.setProductId(oi.getProduct().getProductId());
                }
                ie.setProduct(pe);
                ie.setQuantity(oi.getQuantity());
                ie.setUnitPrice(oi.getUnitPrice());
                entity.addItem(ie);
            }
        }

        return entity;
    }

    @Override
    public OrderDto toDto(OrderEntity orderEntity) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        OrderDto dto = new OrderDto(orderEntity.getCustomerId(), itemDtos, orderEntity.getStatus());

        if (orderEntity.getItems() != null) {
            for (OrderItemEntity ie : orderEntity.getItems()) {
                ProductDto pdto = productMapper.toDto(ie.getProduct());
                OrderItemDto oidto = new OrderItemDto(dto, pdto, ie.getQuantity(), ie.getUnitPrice());
                itemDtos.add(oidto);
            }
        }

        return dto;
    }

}
