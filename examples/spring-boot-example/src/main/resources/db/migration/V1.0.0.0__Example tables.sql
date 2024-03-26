CREATE TABLE product_group (
    id INT NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE product (
    id INT NOT NULL PRIMARY KEY,
    active BOOLEAN NOT NULL,
    description VARCHAR(255) NOT NULL,
    product_group_id INT NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    CONSTRAINT product_product_group_fk FOREIGN KEY (product_group_id) REFERENCES product_group (id)
);
