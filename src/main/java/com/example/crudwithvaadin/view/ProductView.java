package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.Product;
import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.form.ProductEditorForm;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.example.crudwithvaadin.repository.ProductRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;


@Route("products")
public class ProductView extends VerticalLayout {

    private final ProductRepository repo;
    private final ProductCategoryRepository catRepo;
    private final ProductEditorForm editor;


    private TextField filter = new TextField();

    private Button addNewBtn;
    private Grid<Product> grid = new Grid<>(Product.class);

    public ProductView(ProductRepository repository, ProductCategoryRepository catRepo, ProductEditorForm editor) {
        this.repo = repository;
        this.catRepo = catRepo;
        this.editor = editor;
        this.grid = new Grid<>(Product.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New Product", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid);

        grid.setHeight("300px");
        grid.setColumns("id", "price", "name", "dateProcessed", "productCategory.name");
        grid.getColumnByKey("id").setWidth("80px").setFlexGrow(0);

        filter.setPlaceholder("Filter by name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listProducts(e.getValue()));

        // Connect selected Product to editor or hide if none is selected
         grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editProduct(e.getValue());
        });

        // Instantiate and edit new Product the new button is clicked
        ProductCategory categoryMeat = catRepo.findByName("meat");

        Product product = new Product();
        product.setName("Product1");
        product.setPrice(9.99);
        product.setProductCategory(categoryMeat);

        addNewBtn.addClickListener(e -> editor.editProduct(product));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listProducts(filter.getValue());
        });

        // Initialize listing
        listProducts(null);
        add(actions, grid);
    }

    // tag::listProducts[]
    void listProducts(String filterText) {
        if (StringUtils.isEmpty(filterText) || repo.findByName(filterText)  == null) {
        grid.setItems(repo.findAll());
        }
        else {
        grid.setItems(repo.findByName(filterText));
        }
        repo.findAll();
    }
}
