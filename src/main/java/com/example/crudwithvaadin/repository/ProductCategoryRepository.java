package com.example.crudwithvaadin.repository;


import com.example.crudwithvaadin.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for ProductCategory
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
