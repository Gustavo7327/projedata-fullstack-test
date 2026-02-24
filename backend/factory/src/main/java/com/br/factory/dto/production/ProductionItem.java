package com.br.factory.dto.production;

public record ProductionItem(
    Long productId,
    String productName,
    Integer quantityToProduce,
    Double unitValue,
    Double totalValue
) {
    
}
