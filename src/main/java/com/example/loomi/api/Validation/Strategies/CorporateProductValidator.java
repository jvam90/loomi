package com.example.loomi.api.Validation.Strategies;

import org.springframework.stereotype.Component;

import com.example.loomi.api.Validation.ProductValidationException;
import com.example.loomi.api.Validation.ProductValidator;
import com.example.loomi.domain.Entities.Product;
import com.example.loomi.domain.Enums.ProductType;
import com.example.loomi.infrastructure.JPAEntities.OrderItemEntity;

@Component
public class CorporateProductValidator implements ProductValidator {

    @Override
    public ProductType supportedType() {
        return ProductType.CORPORATE;
    }

    @Override
    public void validate(Product product) throws ProductValidationException {
        // validações para produtos corporativos
    }

    @Override
    public void validate(OrderItemEntity orderItemEntity) throws ProductValidationException {
        if (!validateCustomerInformation(orderItemEntity.getOrder().getCustomerId())) {
            throw new ProductValidationException("Invalid CNPJ and state registration for customer ID: "
                    + orderItemEntity.getOrder().getCustomerId());
        }

        if (!validateCustomerCreditLimit(orderItemEntity.getOrder().getCustomerId())) {
            throw new ProductValidationException("Invalid credit limit for customer ID: "
                    + orderItemEntity.getOrder().getCustomerId());
        }
    }

    // Mock para validar CNPJ e Inscrição Estadual
    private boolean validateCustomerInformation(String customerId) {
        // Implementar a lógica de validação do CNPJ e Inscrição Estadual -> mock
        return true; // Retornar true se válido, false caso contrário
    }

    // Mock para validar CNPJ e Inscrição Estadual
    private boolean validateCustomerCreditLimit(String customerId) {
        // Implementar a lógica de validação de limite de crédito do cliente -> mock
        return true; // Retornar true se válido, false caso contrário
    }

}
