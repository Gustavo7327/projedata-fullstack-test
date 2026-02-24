package com.br.factory.dto.productcomposition;

import com.br.factory.dto.rawmaterial.RawMaterialResponse;
import com.br.factory.entity.ProductComposition;

public record ProductCompositionResponse(
    Long id,
    RawMaterialResponse rawMaterial,
    Integer quantityRequired
) {
    
    public static ProductCompositionResponse fromEntity(ProductComposition composition) {
        return new ProductCompositionResponse(
            composition.getId(),
            RawMaterialResponse.fromEntity(composition.getRawMaterial()),
            composition.getQuantityRequired()
        );
    }
}
