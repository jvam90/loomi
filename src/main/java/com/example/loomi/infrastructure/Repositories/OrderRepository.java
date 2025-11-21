package com.example.loomi.infrastructure.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByCustomerId(String customerId);
    Optional<OrderEntity> findByOrderIdAndCustomerId(String orderId, String customerId);
}
