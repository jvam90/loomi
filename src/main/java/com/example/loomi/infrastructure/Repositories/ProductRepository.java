package com.example.loomi.infrastructure.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String>{
    
}
