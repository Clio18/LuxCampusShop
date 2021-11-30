package com.luxcampus.shopOnline.service;

import com.luxcampus.shopOnline.dao.ProductDAO;
import com.luxcampus.shopOnline.entity.Product;

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
        return productDAO.save(product);
    }
    public boolean delete(int id){
        return productDAO.delete(id);
    }
    public boolean update(Product product){
        return productDAO.update(product);
    }
}
