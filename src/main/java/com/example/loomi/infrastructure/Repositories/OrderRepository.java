package com.example.loomi.infrastructure.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loomi.infrastructure.JPAEntities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    
}
