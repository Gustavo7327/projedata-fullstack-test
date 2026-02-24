package com.br.factory.dto.product;

import java.util.List;

import com.br.factory.dto.productcomposition.ProductCompositionResponse;
import com.br.factory.entity.Product;

public record ProductResponse(
    Long code,
    String name,
    Double value,
    List<ProductCompositionResponse> compositions
) {
    
    public static ProductResponse fromEntity(Product product) {
        List<ProductCompositionResponse> compositions = product.getCompositions().stream()
            .map(ProductCompositionResponse::fromEntity)
            .toList();
        
        return new ProductResponse(
            product.getCode(),
            product.getName(),
            product.getValue(),
            compositions
        );
    }
}
