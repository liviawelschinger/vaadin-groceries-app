package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.form.ProductCategoryEditorForm;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import org.springframework.util.StringUtils;
import com.vaadin.flow.component.button.Button;


/**
 * MainView that shows the product categories
 */
@Route(value = "categories")
public class ProductCategoryView extends VerticalLayout {

    private final ProductCategoryRepository repo;

    private final ProductCategoryEditorForm editor;

    final Grid<ProductCategory> grid;

    final TextField filter;

    private final Button addNewBtn;

    public ProductCategoryView(ProductCategoryRepository repo, ProductCategoryEditorForm editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(ProductCategory.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New category", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

        MenuBar menuBar = new MenuBar();
        menuBar.setOpenOnHover(true);

        // Menu tab View
        MenuItem view = menuBar.addItem("View");

        // Sub menu of View with navigation links
        SubMenu viewSubMenu = view.getSubMenu();
        // getSource gets the referred component
        // getUI gets the UI this component is attached to.
        viewSubMenu.addItem("Product categories", event -> event.getSource().getUI().ifPresent(ui -> ui.navigate("categories")));
        viewSubMenu.addItem("Products", event -> event.getSource().getUI().ifPresent(ui -> ui.navigate("products")));

        add(menuBar, actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "name", "dateProcessed"); // use names of the Java attributes here, not the SQL columns
        grid.getColumnByKey("id").setWidth("80px").setFlexGrow(0);

        filter.setPlaceholder("Filter by name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listProductCategory(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editProductCategory(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editProductCategory(new ProductCategory( "", LocalDate
            .now())));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listProductCategory(filter.getValue());
        });

        // Initialize listing
        listProductCategory(null);
    }
    void listProductCategory(String filterText) {
       if (StringUtils.isEmpty(filterText) || repo.findByName(filterText)  == null) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByName(filterText));
        }
        repo.findAll();
    }
}