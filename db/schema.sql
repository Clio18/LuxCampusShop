CREATE TABLE product (
        product_id serial PRIMARY KEY,
        name VARCHAR ( 50 ) NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        created_on TIMESTAMP NOT NULL
        );

CREATE TABLE users (
        id serial PRIMARY KEY,
        name VARCHAR ( 100 ) NOT NULL,
        last_Name VARCHAR ( 100 ) NOT NULL,
        email VARCHAR ( 100 ) NOT NULL,
        password VARCHAR ( 100 ) NOT NULL
        );