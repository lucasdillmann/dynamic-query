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

INSERT INTO product_group (id, description)
VALUES (1, 'Fruits'),
       (2, 'Dairy'),
       (3, 'Meat'),
       (4, 'Beverages'),
       (5, 'Vegetables');

INSERT INTO product (id, active, description, product_group_id, unit_price)
VALUES (1, true, 'Apple', 1, 0.25),
       (2, true, 'Banana', 1, 0.33),
       (3, true, 'Potato', 5, 0.20),
       (4, true, 'Onion', 5, 1.32),
       (5, true, 'Butter', 2, 1.99),
       (6, true, 'Chicken', 3, 1.99),
       (7, true, 'Bacon', 3, 2.95),
       (8, true, 'Orange', 5, 0.65);
