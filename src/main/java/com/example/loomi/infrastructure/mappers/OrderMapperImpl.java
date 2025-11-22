package com.example.loomi.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.loomi.api.dtos.OrderDto;
import com.example.loomi.api.dtos.OrderItemDto;
import com.example.loomi.api.dtos.ProductDto;
import com.example.loomi.domain.Entities.Order;
import com.example.loomi.domain.Entities.OrderItem;
import com.example.loomi.domain.Enums.OrderStatus;
import com.example.loomi.infrastructure.JPAEntities.OrderEntity;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;
import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

@Component
public class OrderMapperImpl implements IOrderMapper {

    private final IProductMapper productMapper;

    public OrderMapperImpl(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /*
     * A estrutura dos mapeamentos foi ajustada para refletir a nova composição
     * entre Order e OrderItem,
     * garantindo que os itens do pedido sejam corretamente convertidos entre as
     * entidades JPA,
     * domínios e DTOs. Para separar domínio de detalhes de implementação de
     * persistência (JPA),
     * foram usados OrderEntity, OrderItemEntity e ProductEntity nas conversões,
     * evitando a exposição direta das entidades JPA.
     * Uso do Copilot: analisar a estrutura das classes envolvidas e sugerir
     * implementações de métodos de mapeamento que respeitem as relações entre elas.
     */

    @Override
    public Order toDomainOrder(OrderEntity entity) {
        List<OrderItem> items = new ArrayList<>();
        Order order = new Order(entity.getOrderId(), entity.getCustomerId(), items, entity.getStatus());
        order.setCreatedAt(entity.getCreatedAt());
        order.setUpdatedAt((entity.getUpdatedAt()));
        order.setTotal(entity.getTotal());
        if (entity.getItems() != null) {
            for (OrderItemEntity orderItemEntity : entity.getItems()) {
                var productEntity = orderItemEntity.getProduct();
                var product = productMapper.toDomainProduct(productEntity);
                OrderItem orderItem = new OrderItem(product, orderItemEntity.getQuantity(),
                        orderItemEntity.getUnitPrice(), orderItemEntity.getActivationKey(),
                        orderItemEntity.getMetadata());
                orderItem.setId(orderItemEntity.getId());
                orderItem.setOrder(order);
                items.add(orderItem);
            }
        }

        return order;
    }

    @Override
    public OrderEntity toJPAEntityFromDto(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(orderDto.getStatus() != null ? orderDto.getStatus() : OrderStatus.PENDING);
        orderEntity.setCustomerId(orderDto.getCustomerId());

        if (orderDto.getItems() != null) {
            for (OrderItemDto orderItemDto : orderDto.getItems()) {
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                // não setamos o id do item aqui, pois é gerado pelo banco
                ProductEntity productEntity = new ProductEntity();
                if (orderItemDto.getProduct() != null) {
                    productEntity.setProductId(orderItemDto.getProduct().getProductId());
                } else {
                    productEntity.setProductId(orderItemDto.getProductId());
                }
                orderItemEntity.setProduct(productEntity);
                orderItemEntity.setQuantity(orderItemDto.getQuantity());
                orderItemEntity.setUnitPrice(orderItemDto.getUnitPrice());
                orderItemEntity.setMetadata(orderItemDto.getMetadata());
                orderItemEntity.setOrder(orderEntity);
                orderEntity.addItem(orderItemEntity);
            }
        }

        return orderEntity;
    }

    @Override
    public OrderDto toDto(OrderEntity orderEntity) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        OrderDto orderDto = new OrderDto(orderEntity.getCustomerId(), itemDtos, orderEntity.getStatus());
        orderDto.setCreatedAt(orderEntity.getCreatedAt());
        orderDto.setUpdatedAt((orderEntity.getUpdatedAt()));
        orderDto.setTotal(orderEntity.getTotal());
        if (orderEntity.getItems() != null) {
            for (OrderItemEntity orderItemEntity : orderEntity.getItems()) {
                ProductDto pdto = productMapper.toDto(orderItemEntity.getProduct());
                OrderItemDto orderItemdto = new OrderItemDto(orderDto, pdto, orderItemEntity.getQuantity(),
                        orderItemEntity.getUnitPrice(), orderItemEntity.getActivationKey(),
                        orderItemEntity.getMetadata());
                itemDtos.add(orderItemdto);
            }
        }

        return orderDto;
    }

}
