package com.br.factory.dto.product;

import java.util.List;

import com.br.factory.dto.productcomposition.ProductCompositionRequest;
import com.br.factory.entity.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "Value is required") @Positive(message = "Value must be positive") Double value,
    @Valid List<ProductCompositionRequest> compositions
) {
    
    public Product toEntity() {
        return new Product(name, value);
    }
}

