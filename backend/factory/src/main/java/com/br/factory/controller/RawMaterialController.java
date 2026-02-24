package com.br.factory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.factory.dto.rawmaterial.RawMaterialRequest;
import com.br.factory.dto.rawmaterial.RawMaterialResponse;
import com.br.factory.dto.rawmaterial.RawMaterialUpdate;
import com.br.factory.entity.RawMaterial;
import com.br.factory.service.RawMaterialService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {
    
    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }


    @PostMapping
    public ResponseEntity<RawMaterialResponse> createRawMaterial(@Valid @RequestBody RawMaterialRequest dto) {
        RawMaterial rawMaterial = rawMaterialService.createRawMaterial(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(RawMaterialResponse.fromEntity(rawMaterial));
    }


    @GetMapping("/{code}")
    public ResponseEntity<RawMaterialResponse> getRawMaterialById(@PathVariable Long code) {
        RawMaterial rawMaterial = rawMaterialService.findRawMaterialByCode(code);
        return ResponseEntity.ok(RawMaterialResponse.fromEntity(rawMaterial));
    }


    @GetMapping
    public ResponseEntity<List<RawMaterialResponse>> getAllRawMaterials() {
        List<RawMaterial> rawMaterials = rawMaterialService.getAllRawMaterials();
        List<RawMaterialResponse> responses = rawMaterials.stream()
            .map(RawMaterialResponse::fromEntity)
            .toList();
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{code}")
    public ResponseEntity<RawMaterialResponse> updateRawMaterial(
        @PathVariable Long code,
        @Valid @RequestBody RawMaterialUpdate dto
    ) {
        RawMaterial rawMaterial = rawMaterialService.updateRawMaterial(dto, code);
        return ResponseEntity.ok(RawMaterialResponse.fromEntity(rawMaterial));
    }
    

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long code) {
        rawMaterialService.deleteRawMaterial(code);
        return ResponseEntity.noContent().build();
    }
}
