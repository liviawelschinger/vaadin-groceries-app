package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;



/**
 * MainView that shows the product categories
 */
@Route(value = "categories")
public class MainView extends VerticalLayout {

    private final ProductCategoryRepository categoryRepository;
    private final Grid<ProductCategory> categoryGrid;
    private final TextField filter;
    private Binder<ProductCategory> binder = new Binder<>(ProductCategory.class);


    /**
     * Constructor
     * @param categoryRepository ProductCategoryRepository
     */
    public MainView(ProductCategoryRepository categoryRepository) {


        this.categoryRepository = categoryRepository;
        this.filter = new TextField();
        filter.setPlaceholder("Filter category by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> {
            listProductCategories(event.getValue());
        });
        this.categoryGrid = new Grid<>(ProductCategory.class); // Grid bound to the ProductCategory list
        add(filter, categoryGrid);

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
