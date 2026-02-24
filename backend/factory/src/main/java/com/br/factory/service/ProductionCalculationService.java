package com.br.factory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.br.factory.dto.production.ProductionItem;
import com.br.factory.dto.production.ProductionSuggestionResponse;
import com.br.factory.entity.Product;
import com.br.factory.entity.ProductComposition;
import com.br.factory.entity.RawMaterial;

@Service
public class ProductionCalculationService {
    
    private final ProductService productService;
    private final RawMaterialService rawMaterialService;

    public ProductionCalculationService(
        ProductService productService,
        RawMaterialService rawMaterialService
    ) {
        this.productService = productService;
        this.rawMaterialService = rawMaterialService;
    }

    public ProductionSuggestionResponse calculateOptimalProduction() {
        List<Product> allProducts = productService.getAllProducts();
        Map<Long, Integer> currentStock = getCurrentStock();

        if (allProducts.isEmpty()) {
            return new ProductionSuggestionResponse(
                new ArrayList<>(),
                0.0,
                "No products available for production"
            );
        }

        List<ProductionItem> suggestions = new ArrayList<>();
        Map<Long, Integer> workingStock = new HashMap<>(currentStock);
        double totalValue = 0.0;

        boolean productionFound;
        do {
            productionFound = false;
            ProductionItem bestOption = null;
            int bestProductIndex = -1;

            for (int i = 0; i < allProducts.size(); i++) {
                Product product = allProducts.get(i);
                int maxQuantity = calculateMaxQuantity(product, workingStock);

                if (maxQuantity > 0) {
                    ProductionItem item = new ProductionItem(
                        product.getId(),
                        product.getName(),
                        maxQuantity,
                        product.getValue(),
                        product.getValue() * maxQuantity
                    );

                    if (bestOption == null || item.totalValue() > bestOption.totalValue()) {
                        bestOption = item;
                        bestProductIndex = i;
                        productionFound = true;
                    }
                }
            }

            if (productionFound && bestOption != null) {
                suggestions.add(bestOption);
                totalValue += bestOption.totalValue();
                deductStock(allProducts.get(bestProductIndex), bestOption.quantityToProduce(), workingStock);
            }
        } while (productionFound);

        return new ProductionSuggestionResponse(
            suggestions,
            totalValue,
            generateMessage(suggestions, totalValue)
        );
    }

    
    private int calculateMaxQuantity(Product product, Map<Long, Integer> stock) {
        if (product.getCompositions() == null || product.getCompositions().isEmpty()) {
            return 0;
        }

        int maxQuantity = Integer.MAX_VALUE;

        for (ProductComposition composition : product.getCompositions()) {
            Long rawMaterialId = composition.getRawMaterial().getId();
            Integer availableStock = stock.getOrDefault(rawMaterialId, 0);
            Integer required = composition.getQuantityRequired();

            int possibleQuantity = availableStock / required;
            maxQuantity = Math.min(maxQuantity, possibleQuantity);
        }

        return maxQuantity == Integer.MAX_VALUE ? 0 : maxQuantity;
    }

    
    private void deductStock(Product product, Integer quantity, Map<Long, Integer> stock) {
        for (ProductComposition composition : product.getCompositions()) {
            Long rawMaterialId = composition.getRawMaterial().getId();
            Integer currentStock = stock.getOrDefault(rawMaterialId, 0);
            Integer deduction = composition.getQuantityRequired() * quantity;
            stock.put(rawMaterialId, currentStock - deduction);
        }
    }

    
    private Map<Long, Integer> getCurrentStock() {
        Map<Long, Integer> stock = new HashMap<>();
        List<RawMaterial> rawMaterials = rawMaterialService.getAllRawMaterials();

        for (RawMaterial rawMaterial : rawMaterials) {
            stock.put(rawMaterial.getId(), rawMaterial.getStockQuantity());
        }

        return stock;
    }

    
    private String generateMessage(List<ProductionItem> suggestions, Double totalValue) {
        if (suggestions.isEmpty()) {
            return "Not enough raw materials to produce any product";
        }

        return String.format(
            "Production optimization suggests manufacturing %d product(s) for a total value of $%.2f",
            suggestions.size(),
            totalValue
        );
    }
}
