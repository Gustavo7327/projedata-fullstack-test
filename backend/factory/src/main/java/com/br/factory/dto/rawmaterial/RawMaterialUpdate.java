package com.br.factory.dto.rawmaterial;

import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialUpdate(String name, @PositiveOrZero(message = "Stock quantity must be a positive number or zero") Integer stockQuantity) {
    
}
