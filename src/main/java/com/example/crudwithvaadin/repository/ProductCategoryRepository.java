package com.example.crudwithvaadin.repository;


import com.example.crudwithvaadin.entity.ProductCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllOrderById(long id);

    Optional<ProductCategory> findById(long id);
}
