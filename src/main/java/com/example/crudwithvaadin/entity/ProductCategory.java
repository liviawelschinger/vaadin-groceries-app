package com.example.crudwithvaadin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for ProductCategory
 * @author Livia Welschinger
 */
@Entity
@Table(name = "category")
public class ProductCategory {

    @Id
    @GeneratedValue
    private Long id; // use Long for later examiniation (null (not persited category) or not null (persisted category)) The Long class wraps a value of the primitive type long in an object. An object of type Long contains a single field whose type is long.
    private String name;
    private List<Product> productList = new ArrayList<>();

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
