CREATE TABLE product (
    id BIGINT(21) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    date_processed DATE NOT NULL,
    category_id BIGINT(21) NOT NULL,
     foreign key fk_category (category_id) references category(id)
);