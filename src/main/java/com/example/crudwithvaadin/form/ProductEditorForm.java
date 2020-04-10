package com.example.crudwithvaadin.form;

import com.example.crudwithvaadin.entity.Product;
import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import com.example.crudwithvaadin.repository.ProductRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
// Spring-Vaadin integration, same as Spring @Component; Attention: Vaadin has own annotation @Component
@UIScope // that spring beans of this class are created in the access area and with the lifespan of a Vaadin UI session.
public class ProductEditorForm extends VerticalLayout implements KeyNotifier {

    private final ProductRepository repository;

    private final ProductCategoryRepository productCategoryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEditorForm.class);

    /**
     * The currently edited product category
     */
    private Product product;

    /* Fields to edit properties in ProductCategory entity */
    TextField nameField = new TextField("Product name");

    /* Numberfield */
    NumberField priceField = new NumberField("Price");

    String categoryValue;

    /* Combobox for category */
    ComboBox<String> productCategoryComboBox = new ComboBox<>("Category");

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
    public ProductEditorForm(ProductRepository repository
            ,ProductCategoryRepository productCategoryRepository
    ) {

        this.repository = repository;
        this.productCategoryRepository = productCategoryRepository;

        priceField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        priceField.setPrefixComponent(new Icon(VaadinIcon.EURO));

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        List<String> cbItems = new ArrayList<>();
        productCategoryList.forEach(cat -> cbItems.add(cat.getName()));
        productCategoryComboBox.setItems(cbItems);
        productCategoryComboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                categoryValue = "";
            } else {
                categoryValue = event.getValue();
                LOGGER.info("Category (combobox): " + event.getValue());
                LOGGER.info(productCategoryRepository == null ? "productCategoryRepository == null"
                        : "productCategoryRepository != null");
                /* WW NPE */
                ProductCategory cat = productCategoryRepository.findByName(event.getValue());
                LOGGER.info("Category (database): " + cat.getName());
                product.setProductCategory(cat);
                /* */
            }
        });
        add(nameField, priceField, productCategoryComboBox, actions);

        // bind using naming convention
        // WW binder.bindInstanceFields(this);
        // WW: Vaadin Book 6.1 (Page 96)
        binder.bind(nameField, Product::getName, Product::setName);
        binder.bind(priceField, Product::getPrice, Product::setPrice);
        binder.bind(productCategoryComboBox, Product::getCategoryOption, Product::setCategoryOption);


        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        cancel.getElement().getThemeList().add("primary");

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
        LOGGER.info("Product to save: " + product.toString() + " - " + product.getCategoryOption());
        Product savedProduct = repository.save(product);
        LOGGER.info("Product saved: " + savedProduct.toString() + " - " + savedProduct.getCategoryOption());

        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editProduct(Product p) {

        LOGGER.info("editProduct");

        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = p.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            product = repository.findByName(p.getName());
            product.setCategoryOption(product.getProductCategory().getName());
        }
        else {
            product = p;
            ProductCategory cat = productCategoryRepository.findByName("Fruits");
            p.setProductCategory(cat);
            p.setCategoryOption(cat.getName());
        }
        cancel.setVisible(persisted);

        // Bind category properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(product);

        setVisible(true);

        // Focus first name initially
        nameField.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
