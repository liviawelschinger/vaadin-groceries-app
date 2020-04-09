package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.entity.Product;
import com.example.crudwithvaadin.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JPA-SQL: table name has to be exact the same like the name of the Java model class
    @Query(value = "SELECT c FROM Product c WHERE c.name LIKE %:name%")
    Product findByName(@Param("name") String name);

}
