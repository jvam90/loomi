package com.example.loomi.api.Validation;

import java.util.EnumMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.loomi.domain.Enums.ProductType;

//Sugestão do copilot.
@Component
public class ProductValidatorFactory {

    private final EnumMap<ProductType, ProductValidator> registry = new EnumMap<>(ProductType.class);

    /*
     * Construtor vai registrar todos os validadores disponíveis no sistema.
     * Cada validador informa o tipo de produto que suporta através do método
     * supportedType().
     * Havendo mais de um validador para o mesmo tipo, lança uma
     * IllegalStateException.
     * 
     */

    public ProductValidatorFactory(List<ProductValidator> validators) {
        for (ProductValidator v : validators) {
            ProductType t = v.supportedType();
            if (registry.put(t, v) != null) {
                throw new IllegalStateException("Duplicate validator for type " + t);
            }
        }
    }

    /*
     * Método para obter o validador adequado para o tipo de produto fornecido.
     * Lança IllegalArgumentException se não houver validador registrado para o
     * tipo.
     */
    public ProductValidator get(ProductType type) {
        ProductValidator v = registry.get(type);
        if (v == null)
            throw new IllegalArgumentException("No validator for type " + type);
        return v;
    }
}
