package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    String QUERY_BY_NAME = "SELECT p FROM Product p WHERE p.name LIKE :name" + "%";

    @Query(QUERY_BY_NAME)
    Product findByName(@Param("name") String name);
}
