# LuxCampusShop
LuxCampus online shop
1. docker run -e POSTGRES_PASSWORD=postgres -p 8100:5432 -d postgres 
2. CREATE TABLE product (
        product_id serial PRIMARY KEY,
        name VARCHAR ( 50 ) NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        created_on TIMESTAMP NOT NULL
        );
       
