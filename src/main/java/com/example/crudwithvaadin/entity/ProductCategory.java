package com.example.crudwithvaadin.entity;

import java.time.LocalDate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for ProductCategory
 * @author Livia Welschinger
 */
@Entity
@Table(name = "category") // name of SQL table
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // use Long for later examiniation (null (not persited category) or not null (persisted category)) The Long class wraps a value of the primitive type long in an object. An object of type Long contains a single field whose type is long.

    @Column(name = "name")
    private String name;

    @Column(name = "date_processed")
    private LocalDate dateProcessed;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "productCategory") // mapped by Java entitity, must be same name as Java Object in Product class therefore productCategory
    private List<Product> productList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategory() {

    }

    public ProductCategory(String name, LocalDate dateProcessed) {
        this.name = name;
        this.dateProcessed = dateProcessed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
