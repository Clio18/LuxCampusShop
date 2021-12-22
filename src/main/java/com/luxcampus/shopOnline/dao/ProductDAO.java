package com.luxcampus.shopOnline.dao;
import com.luxcampus.shopOnline.entity.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> get();

    boolean save(Product product);

    boolean delete(int id);

    boolean update(Product product);

    Product getById(int id);
}
