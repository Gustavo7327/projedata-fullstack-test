package com.br.factory.dto.rawmaterial;

import com.br.factory.entity.RawMaterial;

public record RawMaterialResponse(
    Long code,
    String name,
    Integer stockQuantity
) {
    
    public static RawMaterialResponse fromEntity(RawMaterial rawMaterial) {
        return new RawMaterialResponse(
            rawMaterial.getCode(),
            rawMaterial.getName(),
            rawMaterial.getStockQuantity()
        );
    }
}
