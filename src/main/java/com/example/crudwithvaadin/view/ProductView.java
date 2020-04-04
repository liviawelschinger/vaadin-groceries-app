package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.Product;
import com.example.crudwithvaadin.form.ProductEditorForm;
import com.example.crudwithvaadin.repository.ProductRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.Route;


@Route("products")
public class ProductView extends VerticalLayout {

    private final ProductRepository repository;
    private TextField filter = new TextField();
    private Button addNewBtn = new Button("New Category", VaadinIcon.PLUS.create());
    private Grid<Product> grid = new Grid<>(Product.class);

    public ProductView(ProductRepository repository) {
        this.repository = repository;

        filter.setPlaceholder("Filter by name");

        grid.setColumns("id", "price", "name");

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid);


    }
}
