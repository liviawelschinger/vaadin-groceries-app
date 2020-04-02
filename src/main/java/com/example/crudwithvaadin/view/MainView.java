package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * MainView that shows the product categories
 */
@Route
public class MainView extends VerticalLayout {

    private final ProductCategoryRepository categoryRepository;
    private Grid<ProductCategory> categoryGrid;

    /**
     * Constructor
     * @param categoryRepository ProductCategoryRepository
     */
    public MainView(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryGrid = new Grid<>(ProductCategory.class); // Grid bound to the ProductCategory list
        add(categoryGrid);
        listProductCategories();

    }


    private void listProductCategories() {
        // pass the list of entities from a constructor-injected ProductCategoryRepository to the Grid by using setItems()
        categoryGrid.setItems(categoryRepository.findAll());
    }

    /*
    + Although Vaadin Grid lazy loads the data from the server to the browser, the preceding approach keeps the whole list of data in the server memory.
        To save some memory, you could show only the topmost results by employing paging or providing a lazy loading data provider by using the
        setDataProvider(DataProvider) method.
     */

}
