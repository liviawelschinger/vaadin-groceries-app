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

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
