package com.br.factory.dto.product;

import jakarta.validation.constraints.Positive;

public record ProductUpdate(
    String name,
    @Positive(message = "Value must be positive") Double value
) {
    
}
