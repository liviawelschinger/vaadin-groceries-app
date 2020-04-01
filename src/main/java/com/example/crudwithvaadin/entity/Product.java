package com.example.crudwithvaadin.entity;

import lombok.Data;

import javax.persistence.*;


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


}
