package com.example.loomi.infrastructure.mappers;

import com.example.loomi.api.dtos.CustomerDto;
import com.example.loomi.domain.Entities.Customer;
import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;

public interface ICustomerMapper {
    Customer toDomainCustomer(CustomerEntity entity);
    CustomerEntity toJPAEntityFromDto(CustomerDto customer);
    CustomerDto toDto(CustomerEntity customerEntity);
}
