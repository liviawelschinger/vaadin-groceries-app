package com.example.crudwithvaadin.repository;


import com.example.crudwithvaadin.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for ProductCategory
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    // JPA-SQL: table name has to be exact the same like the name of the Java model class
     @Query(value = "SELECT c FROM ProductCategory c WHERE c.name LIKE %:name%")
     ProductCategory findByName(@Param("name") String name);
}
