package com.example.crudwithvaadin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Entity class for Product
 */
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private double price;
    @ManyToOne( fetch = FetchType.EAGER)
    private ProductCategory category;


    /**
     * NoArgsConstructor
     */
    public Product() {

    }

    /**
     * AllArgsConstructor
     * @param name product name
     * @param price product price
     * @param category product category
     */
    public Product(String name, double price, ProductCategory category)  {
        this.name = name;
        this.price = price;
        this.category = category;
    }



}
