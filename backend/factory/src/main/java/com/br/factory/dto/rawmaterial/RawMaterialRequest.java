package com.br.factory.dto.rawmaterial;

import com.br.factory.entity.RawMaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialRequest(@NotBlank(message = "Code is required") Long code, @NotBlank(message = "Name is required") String name, @NotBlank(message = "Stock quantity is required") @PositiveOrZero Integer stockQuantity) {
    
    public RawMaterial toEntity() {
        RawMaterial rawMaterial = new RawMaterial(null, name, stockQuantity);
        rawMaterial.setCode(code);
        return rawMaterial;
    }
}