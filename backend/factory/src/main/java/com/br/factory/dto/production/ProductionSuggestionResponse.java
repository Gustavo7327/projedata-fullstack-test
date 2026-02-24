package com.br.factory.dto.production;

import java.util.List;

public record ProductionSuggestionResponse(
    List<ProductionItem> suggestions,
    Double totalValue,
    String message
) {
    
}
