package com.br.factory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.factory.dto.production.ProductionSuggestionResponse;
import com.br.factory.service.ProductionCalculationService;

@RestController
@RequestMapping("/production")
public class ProductionController {
    
    private final ProductionCalculationService productionCalculationService;

    public ProductionController(ProductionCalculationService productionCalculationService) {
        this.productionCalculationService = productionCalculationService;
    }

    @GetMapping("/suggest")
    public ResponseEntity<ProductionSuggestionResponse> suggestOptimalProduction() {
        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();
        return ResponseEntity.ok(response);
    }
}
