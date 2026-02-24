package com.br.factory.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.factory.dto.production.ProductionSuggestionResponse;
import com.br.factory.entity.Product;
import com.br.factory.entity.ProductComposition;
import com.br.factory.entity.RawMaterial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductionCalculationService Tests")
class ProductionCalculationServiceTests {

    @Mock
    private ProductService productService;

    @Mock
    private RawMaterialService rawMaterialService;

    @InjectMocks
    private ProductionCalculationService productionCalculationService;

    private RawMaterial rawMaterial1;
    private RawMaterial rawMaterial2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        rawMaterial1 = new RawMaterial(1L, "Iron", 100);
        rawMaterial1.setCode(101L);
        
        rawMaterial2 = new RawMaterial(2L, "Copper", 50);
        rawMaterial2.setCode(102L);

        product1 = new Product(1L, "Steel Plate", 50.0);
        product1.setCode(201L);
        
        product2 = new Product(2L, "Brass Gear", 100.0);
        product2.setCode(202L);
    }

    @Test
    @DisplayName("Should suggest optimal production with single product")
    void testOptimalProductionWithSingleProduct() {
        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            10 
        );
        product1.setCompositions(List.of(composition1));

        when(productService.getAllProducts()).thenReturn(List.of(product1));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions()).isNotEmpty();
        assertThat(response.suggestions().get(0).productId()).isEqualTo(1L);
        assertThat(response.suggestions().get(0).quantityToProduce()).isEqualTo(10); 
        assertThat(response.suggestions().get(0).totalValue()).isEqualTo(500.0); 
        assertThat(response.totalValue()).isEqualTo(500.0);
    }

    @Test
    @DisplayName("Should suggest optimal production with multiple products")
    void testOptimalProductionWithMultipleProducts() {
        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            10 
        );
        product1.setCompositions(List.of(composition1));

        ProductComposition composition2 = new ProductComposition(
            null,
            product2,
            rawMaterial2,
            5 
        );
        product2.setCompositions(List.of(composition2));

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1, rawMaterial2));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions()).isNotEmpty();
        assertThat(response.suggestions().size()).isEqualTo(2);
        assertThat(response.totalValue()).isGreaterThan(0);
        assertThat(response.suggestions().get(0).unitValue()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Should handle product with multiple raw materials")
    void testProductionWithMultipleRawMaterials() {
        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            10
        );
        ProductComposition composition2 = new ProductComposition(
            null,
            product1,
            rawMaterial2,
            5
        );
        product1.setCompositions(List.of(composition1, composition2));

        when(productService.getAllProducts()).thenReturn(List.of(product1));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1, rawMaterial2));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions()).isNotEmpty();
        assertThat(response.suggestions().get(0).quantityToProduce()).isEqualTo(10);
        assertThat(response.suggestions().get(0).totalValue()).isEqualTo(500.0);
    }

    @Test
    @DisplayName("Should return empty suggestions when no products available")
    void testNoProductsAvailable() {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1, rawMaterial2));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions()).isEmpty();
        assertThat(response.totalValue()).isEqualTo(0.0);
        assertThat(response.message()).contains("No products available");
    }

    @Test
    @DisplayName("Should return empty suggestions when no raw materials available")
    void testInsufficientRawMaterials() {
        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            100 
        );
        product1.setCompositions(List.of(composition1));

        when(productService.getAllProducts()).thenReturn(List.of(product1));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions().size()).isEqualTo(1);
        assertThat(response.suggestions().get(0).quantityToProduce()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should prioritize higher value products")
    void testPrioritizeHigherValueProducts() {
        rawMaterial1.setStockQuantity(20);

        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            10 
        );
        product1.setCompositions(List.of(composition1));

        ProductComposition composition2 = new ProductComposition(
            null,
            product2,
            rawMaterial1,
            10 
        );
        product2.setCompositions(List.of(composition2));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response.suggestions()).isNotEmpty();
        assertThat(response.suggestions().get(0).productId()).isEqualTo(2L);
        assertThat(response.suggestions().get(0).unitValue()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Should handle product with no compositions")
    void testProductWithoutCompositions() {
        product1.setCompositions(new ArrayList<>());

        when(productService.getAllProducts()).thenReturn(List.of(product1));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        assertThat(response).isNotNull();
        assertThat(response.suggestions()).isEmpty();
        assertThat(response.totalValue()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should correctly calculate total value of production")
    void testTotalValueCalculation() {
        ProductComposition composition1 = new ProductComposition(
            null,
            product1,
            rawMaterial1,
            10
        );
        product1.setCompositions(List.of(composition1));

        ProductComposition composition2 = new ProductComposition(
            null,
            product2,
            rawMaterial2,
            10
        );
        product2.setCompositions(List.of(composition2));

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));
        when(rawMaterialService.getAllRawMaterials()).thenReturn(List.of(rawMaterial1, rawMaterial2));

        ProductionSuggestionResponse response = productionCalculationService.calculateOptimalProduction();

        double expectedValue = (50.0 * 10) + (100.0 * 5); 
        assertThat(response.totalValue()).isEqualTo(expectedValue);
    }
}
