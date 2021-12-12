package com.luxcampus.shopOnline.service;

import com.luxcampus.shopOnline.dao.ProductDAO;
import com.luxcampus.shopOnline.entity.Product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class ProductService {
    ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> get() {
        List<Product> products = productDAO.get();
        System.out.println("Products in shop: " + products.size());
        return products;
    }

    public boolean save(Product product){
        product.setCreated_on(LocalDateTime.now());
        return productDAO.save(product);
    }

    public boolean delete(int id){
        return productDAO.delete(id);
    }

    public boolean update(Product product){
        product.setCreated_on(LocalDateTime.now());
        return productDAO.update(product);
    }

}
