package com.example.crudwithvaadin.form;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.beans.EventHandler;


@SpringComponent
// Spring-Vaadin integration, same as Spring @Component; Attention: Vaadin has own annotation @Component
@UIScope // that spring beans of this class are created in the access area and with the lifespan of a Vaadin UI session.
public class ProductCategoryEditorForm extends VerticalLayout implements KeyNotifier {  // KeyNotifier: interface for components that support adding key event listeners to the their root elements

    private final ProductCategoryRepository categoryRepository;
    // Field to edit property in ProductCategory entity
    private TextField categoryName = new TextField("Product category name");

    // Action buttons
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    /*
    Connects one or more {@code Field} components to properties of a backing data
    type such as a bean type. With a binder, input components can be grouped
    together into forms to easily create and update business objects with little
    explicit logic needed to move data between the UI and the data layers of the
    application.
     */
    Binder<ProductCategory> binder = new Binder<>(ProductCategory.class);
    private ChangeHandler changeHandler;

    // Currently edited category
    private ProductCategory category;

    public ProductCategoryEditorForm(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

        add(categoryName, actions);

        // binds member fields found in the given objects
        binder.bindInstanceFields(this);

        // configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        cancel.getElement().getThemeList().add("secondary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, event -> save());

        save.addClickListener(event -> save());
        cancel.addClickListener(event -> editCategory(category));
        delete.addClickListener(event -> delete());
        setVisible(false);
    }

    /**
     * Saves a product category
     */
    private void save() {
        categoryRepository.save(category);
        changeHandler.onChange();

    }

    /**
     * Deletes a product category
     */
    private void  delete() {
        categoryRepository.delete(category);
        changeHandler.onChange();
    }

    public void editCategory(ProductCategory c) {

        if (c == null) {
            // Invisible components don't receive any updates from the client-side
            setVisible(false); // Sets the component visibility value.
            // TODO Use?
            return;
        }

        final boolean persisted = category.getId() != null;

        if(persisted) {
            category = categoryRepository.findById(c.getId()).get();
        } else {
            category = c;
        }

        /*
            When category is persisted display cancel option in editor
            When category is not persisted don't display cancel option in editor
         */
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(category);

        setVisible(true);

        // Focus category name initially
        categoryName.focus();

    }

    public void setChangeHandler(ChangeHandler handler) {
        // ChangeHandler is notified when either save or delete is clicked
        changeHandler = handler;
    }

    public interface ChangeHandler {
        void onChange();
    }


}
