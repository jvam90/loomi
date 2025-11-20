package com.example.loomi.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.example.loomi.api.dtos.CustomerDto;
import com.example.loomi.api.dtos.SubscriptionDto;
import com.example.loomi.domain.Customer;
import com.example.loomi.domain.Subscription;
import com.example.loomi.infrastructure.JPAEntities.CustomerEntity;

@Component
public class CustomerMapper implements ICustomerMapper {

        @Override
        public Customer toDomainCustomer(CustomerEntity entity) {
                Customer customer = new Customer(
                                entity.getCustomerId(),
                                entity.getEmail(),
                                entity.getName());

                customer.setCreatedAt(entity.getCreatedAt());
                customer.setSubscriptions(
                                entity.getSubscriptions().stream()
                                                .map(subscriptionEntity -> new Subscription(
                                                                customer,
                                                                subscriptionEntity.getProductId(),
                                                                subscriptionEntity.getStartDate(),
                                                                subscriptionEntity.getEndDate(),
                                                                subscriptionEntity.getStatus(),
                                                                subscriptionEntity.getUnitPrice()))
                                                .toList());

                return customer;
        }

        @Override
        public CustomerDto toDto(CustomerEntity customerEntity) {

                CustomerDto customerDto = new CustomerDto(
                                customerEntity.getName(),
                                customerEntity.getEmail(),
                                customerEntity.getCreatedAt());

                customerDto.setSubscriptions(
                                customerEntity.getSubscriptions().stream()
                                                .map(subscriptionEntity -> new SubscriptionDto(
                                                                customerDto,
                                                                subscriptionEntity.getProductId(),
                                                                subscriptionEntity.getStartDate(),
                                                                subscriptionEntity.getEndDate(),
                                                                subscriptionEntity.getStatus(),
                                                                subscriptionEntity.getUnitPrice()))
                                                .toList());

                return customerDto;
        }

        @Override
        public CustomerEntity toJPAEntityFromDto(CustomerDto customer) {
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setCustomerId(null);
                customerEntity.setName(customer.getName());
                customerEntity.setEmail(customer.getEmail());
                return customerEntity;
        }

}
