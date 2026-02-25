package com.br.factory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.factory.dto.rawmaterial.RawMaterialRequest;
import com.br.factory.dto.rawmaterial.RawMaterialUpdate;
import com.br.factory.entity.RawMaterial;
import com.br.factory.exception.ConflictException;
import com.br.factory.exception.ResourceNotFoundException;
import com.br.factory.repository.ProductCompositionRepository;
import com.br.factory.repository.RawMaterialRepository;

@Service
public class RawMaterialService {
    
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductCompositionRepository productCompositionRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository, ProductCompositionRepository productCompositionRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.productCompositionRepository = productCompositionRepository;
    }


    public RawMaterial createRawMaterial(RawMaterialRequest dto) {
        RawMaterial rawMaterial = dto.toEntity();
        return rawMaterialRepository.save(rawMaterial);
    }


    public RawMaterial updateRawMaterial(RawMaterialUpdate dto, Long code) {
        Optional<RawMaterial> optional = rawMaterialRepository.findById(code);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("RawMaterial", code.toString());
        }
        RawMaterial rawMaterial = optional.get();

        if (dto.name() != null && !dto.name().isEmpty()) {
            rawMaterial.setName(dto.name());
        }

        rawMaterial.setStockQuantity(dto.stockQuantity());
        
        return rawMaterialRepository.save(rawMaterial);
    }


    public RawMaterial findRawMaterialByCode(Long code) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("RawMaterial", code.toString()));
        return rawMaterial;
    }


    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }


    public void deleteRawMaterial(Long code) {
        if (!rawMaterialRepository.existsById(code)) {
            throw new ResourceNotFoundException("RawMaterial", code.toString());
        }
        
        var productCompositions = productCompositionRepository.findByRawMaterialCode(code);
        if (!productCompositions.isEmpty()) {
            throw new ConflictException("Cannot delete the raw material because " + productCompositions.size() + " product(s) using it exist");
        }
        
        rawMaterialRepository.deleteById(code);
    }
}
