package com.example.crudwithvaadin;

import com.example.crudwithvaadin.entity.ProductCategory;
import com.example.crudwithvaadin.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class
 */
@SpringBootApplication
public class CrudWithVaadinApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrudWithVaadinApplication.class);


	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CrudWithVaadinApplication.class, args);
	}

	/**
	 * Loads data from the in-memory database
	 * @param categoryRepository ProdcutCategoryRepositry
	 * @return args
	 */
	@Bean
	public CommandLineRunner loadData(ProductCategoryRepository categoryRepository) {
		return (args) -> {
			// save a couple of product categories
			categoryRepository.save(new ProductCategory("Fruits"));
			categoryRepository.save(new ProductCategory("Vegetables"));
			categoryRepository.save(new ProductCategory("Meat"));
			categoryRepository.save(new ProductCategory("Dairy Goods"));

			// fetch all categories
			LOGGER.info("Categories found with findAll():");
			LOGGER.info("--------");
			for (ProductCategory category : categoryRepository.findAll()) {
				LOGGER.info(category.toString());
			}

			// fetch an individual category by ID
			ProductCategory category = categoryRepository.findById(1L).get();

			LOGGER.info("Product category found with findOne(1L):");
			LOGGER.info("--------------------------------");
			LOGGER.info(category.toString());
			LOGGER.info("");


		};
	}

}
