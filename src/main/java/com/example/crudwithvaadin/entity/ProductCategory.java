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
    private Long id; // use Long for later examiniation (null (not persited category) or not null (persisted category)) The Long class wraps a value of the primitive type long in an object. An object of type Long contains a single field whose type is long.
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

    public Long getId() {
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
