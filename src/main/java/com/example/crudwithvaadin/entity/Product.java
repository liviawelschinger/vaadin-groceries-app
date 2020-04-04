package com.example.crudwithvaadin.entity;

import javax.persistence.*;


/**
 * Entity class for Product
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double price;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    // @JsonIgnor
    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
