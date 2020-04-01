package com.example.crudwithvaadin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductCategory {

    @Id
    @GeneratedValue
    private long id;
    private String name;
}
