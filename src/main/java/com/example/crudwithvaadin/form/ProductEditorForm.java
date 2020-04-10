package com.example.crudwithvaadin.form;

import com.example.crudwithvaadin.entity.Product;
import com.example.crudwithvaadin.repository.ProductRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
// Spring-Vaadin integration, same as Spring @Component; Attention: Vaadin has own annotation @Component
@UIScope // that spring beans of this class are created in the access area and with the lifespan of a Vaadin UI session.
public class ProductEditorForm extends VerticalLayout implements KeyNotifier {

    private final ProductRepository repository;

    /**
     * The currently edited product category
     */
    private Product product;

    /* Fields to edit properties in ProductCategory entity */
    TextField name = new TextField("Product name");

    /* DatePicker */
    DatePicker datum = new DatePicker("Datum");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Product> binder = new Binder<>(Product.class);
    private ChangeHandler changeHandler;

    @Autowired
    public ProductEditorForm(ProductRepository repository) {
        this.repository = repository;

        add(name, datum, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editProduct(product));
        setVisible(false);
    }

    void delete() {
        repository.delete(product);
        changeHandler.onChange();
    }

    void save() {
        repository.save(product);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editProduct(Product p) {
        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = p.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            product = repository.findByName(p.getName());
        }
        else {
            product = p;
        }
        cancel.setVisible(persisted);

        // Bind category properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(product);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
