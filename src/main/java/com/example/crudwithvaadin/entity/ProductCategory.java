package com.example.crudwithvaadin.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity class for ProductCategory
 * @author Livia Welschinger
 */
@Entity
@Data
public class ProductCategory {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    /**
     * NoArgsConstructor
     */
    public ProductCategory() {

    }

    /**
     * AllArgsConstructor
     * @param name product category name
     */
    public ProductCategory(String name) {
        this.name = name;
    }
}
