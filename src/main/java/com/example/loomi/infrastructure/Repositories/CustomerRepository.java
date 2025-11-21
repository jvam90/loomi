package com.example.loomi.infrastructure.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;

import jakarta.persistence.LockModeType;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CustomerEntity c where c.customerId = :id")
    Optional<CustomerEntity> findByIdForUpdate(@Param("id") String id);

    Optional<CustomerEntity> findByEmail(String email);

}
