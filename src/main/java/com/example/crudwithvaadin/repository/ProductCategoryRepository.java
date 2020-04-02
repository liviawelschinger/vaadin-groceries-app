package com.example.crudwithvaadin.repository;


import com.example.crudwithvaadin.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for ProductCategory
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

     String QUERY_BY_NAME = "SELECT c FROM ProductCategory c WHERE c.name LIKE :name" + "%";

     @Query(QUERY_BY_NAME)
     ProductCategory findByName(@Param("name") String name);
}
