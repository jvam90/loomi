package com.example.loomi.infrastructure.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.loomi.infrastructure.JPAEntities.ProductEntity;

import jakarta.persistence.LockModeType;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    /*
     * Aqui, como o sistema tem de validar no banco o estoque disponível antes de
     * criar um pedido,
     * uma transação com lock pessimista é necessária para evitar condições de
     * corrida.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.productId = :id")
    Optional<ProductEntity> findByProductIdForUpdate(String id);
}
