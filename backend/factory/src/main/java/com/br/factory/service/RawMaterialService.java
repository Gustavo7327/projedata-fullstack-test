package com.br.factory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.factory.dto.rawmaterial.RawMaterialRequest;
import com.br.factory.dto.rawmaterial.RawMaterialUpdate;
import com.br.factory.entity.RawMaterial;
import com.br.factory.exception.ResourceNotFoundException;
import com.br.factory.repository.RawMaterialRepository;

@Service
public class RawMaterialService {
    
    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }


    public RawMaterial createRawMaterial(RawMaterialRequest dto) {
        RawMaterial rawMaterial = dto.toEntity();
        return rawMaterialRepository.save(rawMaterial);
    }


    public RawMaterial updateRawMaterial(RawMaterialUpdate dto, Long id) {
        Optional<RawMaterial> optional = rawMaterialRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("RawMaterial", id.toString());
        }
        RawMaterial rawMaterial = optional.get();

        if (dto.name() != null && !dto.name().isEmpty()) {
            rawMaterial.setName(dto.name());
        }

        rawMaterial.setStockQuantity(dto.stockQuantity());
        
        return rawMaterialRepository.save(rawMaterial);
    }


    public RawMaterial findRawMaterialById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RawMaterial", id.toString()));
        return rawMaterial;
    }


    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }


    public void deleteRawMaterial(Long id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new ResourceNotFoundException("RawMaterial", id.toString());
        }
        rawMaterialRepository.deleteById(id);
    }
}
