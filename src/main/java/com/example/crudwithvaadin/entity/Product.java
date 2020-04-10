package com.example.crudwithvaadin.entity;

import java.time.LocalDate;
import javax.persistence.*;

/**
 * Entity class for Product
 */

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "date_processed")
    private LocalDate dateProcessed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id") // reference SQL column name
    // @JsonIgnore
    private ProductCategory productCategory;

    // Not saved in database, for GUI purposes only
    @Transient
    private String categoryOption;

    public Product() {

    }
    public Product(String name, double price, LocalDate dateProcessed, ProductCategory productCategory, String categoryOption) {
        this.name = name;
        this.price = price;
        this.dateProcessed = dateProcessed;
        this.productCategory = productCategory;
        this.categoryOption = categoryOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getCategoryOption() {
        return categoryOption;
    }

    public void setCategoryOption(String categoryOption) {
        this.categoryOption = categoryOption;
    }
}
