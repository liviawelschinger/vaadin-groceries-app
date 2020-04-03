package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.form.ProductCategoryEditorForm;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;
import com.vaadin.flow.component.button.Button;

import java.awt.*;


/**
 * MainView that shows the product categories
 */
@Route(value = "categories")
public class MainView extends VerticalLayout {

    private final ProductCategoryRepository categoryRepository;
    private final Grid<ProductCategory> categoryGrid;
    private final TextField filter;
    private final  Button addNewBtn;
    private final ProductCategoryEditorForm editor;

    /**
     * Constructor
     * @param categoryRepository ProductCategoryRepository
     */
    public MainView(ProductCategoryRepository categoryRepository, ProductCategoryEditorForm editor) {

        this.categoryRepository = categoryRepository;
        this.editor = editor;

        this.filter = new TextField();
        filter.setPlaceholder("Filter category by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> {
            listProductCategories(event.getValue());
        });
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

        this.categoryGrid = new Grid<>(ProductCategory.class); // Grid bound to the ProductCategory list
        categoryGrid.setHeight("300px");
        categoryGrid.setColumns("id", "name");
        categoryGrid.getColumnByKey("id").setWidth("80px").setFlexGrow(0);
        categoryGrid.asSingleSelect().addValueChangeListener(event -> {
            editor.editProductCategory(event.getValue());
                });


        add(actions, categoryGrid);

        // Initialize listing
        listProductCategories(null);

    }


    private void listProductCategories(String filterText) {
        if(StringUtils.isEmpty(filterText)) {
            categoryGrid.setItems(categoryRepository.findAll());
        } else {
            categoryGrid.setItems(categoryRepository.findByName(filterText));
        }


    }

    /*
    + Although Vaadin Grid lazy loads the data from the server to the browser, the preceding approach keeps the whole list of data in the server memory.
        To save some memory, you could show only the topmost results by employing paging or providing a lazy loading data provider by using the
        setDataProvider(DataProvider) method.
     */

}
