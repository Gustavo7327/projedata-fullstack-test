package com.br.factory.dto.productcomposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCompositionRequest(
    @NotNull(message = "Raw material ID is required") Long rawMaterialId,
    @NotNull(message = "Quantity is required") @Positive(message = "Quantity must be positive") Integer quantityRequired
) {
    
}
