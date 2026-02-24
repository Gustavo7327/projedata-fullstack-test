package com.br.factory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.factory.entity.ProductComposition;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition, Long>{
    
    List<ProductComposition> findByProductId(Long productId);
}
