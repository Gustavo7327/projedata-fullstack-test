package com.br.factory.dto.rawmaterial;

import com.br.factory.entity.RawMaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialRequest(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "Stock quantity is required") @PositiveOrZero Integer stockQuantity
) {
    
    public RawMaterial toEntity() {
        RawMaterial rawMaterial = new RawMaterial(name, stockQuantity);
        return rawMaterial;
    }
}