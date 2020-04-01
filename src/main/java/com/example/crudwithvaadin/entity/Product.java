package com.example.crudwithvaadin.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private double price;
    @ManyToOne( fetch = FetchType.EAGER)
    private ProductCategory category;


}
