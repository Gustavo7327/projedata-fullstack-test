package com.br.factory.dto.rawmaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialUpdate(String name, @NotBlank(message = "Stock quantity is required") @PositiveOrZero Integer stockQuantity) {
    
}
