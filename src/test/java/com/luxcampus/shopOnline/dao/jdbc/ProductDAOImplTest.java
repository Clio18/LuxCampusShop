package com.luxcampus.shopOnline.dao.jdbc;

import com.luxcampus.shopOnline.entity.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest {

    @Test
    void get() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        List<Product> products= productDAO.get();
        assertFalse(products.isEmpty());
        for (Product product: products) {
            assertNotEquals(0, product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getPrice());
            assertNotNull(product.getDate());
        }
    }

    @Test
    void save() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        Product product = Product.builder().
                id(200)
                .name("A")
                .price(1000.00)
                .date(LocalDateTime.now())
                .build();
        assertTrue(productDAO.save(product));
    }

    @Test
    void update() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        Product product = Product.builder().
                id(100)
                .name("AA")
                .price(2000.00)
                .date(LocalDateTime.now())
                .build();
        assertTrue(productDAO.save(product));
    }

    @Test
    void delete() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        assertTrue(productDAO.delete(100));
    }


}