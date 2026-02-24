package com.br.factory.dto.production;

public record ProductionItem(
    Long productCode,
    String productName,
    Integer quantityToProduce,
    Double unitValue,
    Double totalValue
) {
    
}
