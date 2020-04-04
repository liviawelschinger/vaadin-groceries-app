package com.example.crudwithvaadin.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for ProductCategory
 * @author Livia Welschinger
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class ProductCategory {

    @Id
    @GeneratedValue
    private Long id; // use Long for later examiniation (null (not persited category) or not null (persisted category)) The Long class wraps a value of the primitive type long in an object. An object of type Long contains a single field whose type is long.

    @Column(name = "name")
    private String name;

    @Column(name = "local_date")
    private LocalDate localDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "productCategory")
    private List<Product> productList = new ArrayList<>();

    public ProductCategory(String name, LocalDate localDate) {
        this.name = name;
        this.localDate = localDate;
    }
}
