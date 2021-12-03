CREATE TABLE product (
        product_id serial PRIMARY KEY,
        name VARCHAR ( 50 ) NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        created_on TIMESTAMP NOT NULL
        );