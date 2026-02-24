package com.br.factory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.factory.dto.product.ProductRequest;
import com.br.factory.dto.product.ProductUpdate;
import com.br.factory.entity.Product;
import com.br.factory.exception.ResourceNotFoundException;
import com.br.factory.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductCompositionService productCompositionService;

    public ProductService(ProductRepository productRepository, ProductCompositionService productCompositionService) {
        this.productRepository = productRepository;
        this.productCompositionService = productCompositionService;
    }


    public Product createProduct(ProductRequest dto) {
        Product product = dto.toEntity();
        Product savedProduct = productRepository.save(product);

        if (dto.compositions() != null && !dto.compositions().isEmpty()) {
            dto.compositions().forEach(compositionRequest -> 
                productCompositionService.addComposition(savedProduct, compositionRequest)
            );
        }

        return savedProduct;
    }


    public Product updateProduct(ProductUpdate dto, Long code) {
        Optional<Product> optional = productRepository.findById(code);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Product", code.toString());
        }
        Product product = optional.get();

        if (dto.name() != null && !dto.name().isEmpty()) {
            product.setName(dto.name());
        }

        if (dto.value() != null) {
            product.setValue(dto.value());
        }
        
        return productRepository.save(product);
    }


    public Product findProductByCode(Long code) {
        return productRepository.findById(code)
            .orElseThrow(() -> new ResourceNotFoundException("Product", code.toString()));
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public void deleteProduct(Long code) {
        if (!productRepository.existsById(code)) {
            throw new ResourceNotFoundException("Product", code.toString());
        }
        productRepository.deleteById(code);
    }
}
