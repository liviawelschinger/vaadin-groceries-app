package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
