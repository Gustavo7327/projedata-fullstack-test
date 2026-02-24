package com.br.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.factory.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
