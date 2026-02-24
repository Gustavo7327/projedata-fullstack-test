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

import com.br.factory.dto.product.ProductRequest;
import com.br.factory.dto.product.ProductResponse;
import com.br.factory.dto.product.ProductUpdate;
import com.br.factory.entity.Product;
import com.br.factory.service.ProductService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest dto) {
        Product product = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromEntity(product));
    }


    @GetMapping("/{code}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long code) {
        Product product = productService.findProductByCode(code);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> responses = products.stream()
            .map(ProductResponse::fromEntity)
            .toList();
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{code}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long code,
        @Valid @RequestBody ProductUpdate dto
    ) {
        Product product = productService.updateProduct(dto, code);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }
    

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long code) {
        productService.deleteProduct(code);
        return ResponseEntity.noContent().build();
    }
}

