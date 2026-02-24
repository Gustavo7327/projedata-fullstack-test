package com.br.factory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.factory.dto.productcomposition.ProductCompositionRequest;
import com.br.factory.entity.Product;
import com.br.factory.entity.ProductComposition;
import com.br.factory.entity.RawMaterial;
import com.br.factory.exception.ResourceNotFoundException;
import com.br.factory.repository.ProductCompositionRepository;
import com.br.factory.repository.RawMaterialRepository;

@Service
public class ProductCompositionService {
    
    private final ProductCompositionRepository productCompositionRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductCompositionService(
        ProductCompositionRepository productCompositionRepository,
        RawMaterialRepository rawMaterialRepository
    ) {
        this.productCompositionRepository = productCompositionRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public ProductComposition addComposition(Product product, ProductCompositionRequest dto) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
            .orElseThrow(() -> new ResourceNotFoundException("RawMaterial", dto.rawMaterialId().toString()));

        ProductComposition composition = new ProductComposition();
        composition.setProduct(product);
        composition.setRawMaterial(rawMaterial);
        composition.setQuantityRequired(dto.quantityRequired());

        return productCompositionRepository.save(composition);
    }

    public List<ProductComposition> getCompositionsByProduct(Long productId) {
        return productCompositionRepository.findByProductId(productId);
    }

    public ProductComposition updateComposition(Long compositionId, ProductCompositionRequest dto) {
        ProductComposition composition = productCompositionRepository.findById(compositionId)
            .orElseThrow(() -> new ResourceNotFoundException("Composition", compositionId.toString()));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
            .orElseThrow(() -> new ResourceNotFoundException("RawMaterial", dto.rawMaterialId().toString()));

        composition.setRawMaterial(rawMaterial);
        composition.setQuantityRequired(dto.quantityRequired());

        return productCompositionRepository.save(composition);
    }

    public void deleteComposition(Long compositionId) {
        if (!productCompositionRepository.existsById(compositionId)) {
            throw new ResourceNotFoundException("Composition", compositionId.toString());
        }
        productCompositionRepository.deleteById(compositionId);
    }
}

