package com.luxcampus.shopOnline.dao.jdbc;

import com.luxcampus.shopOnline.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ShopRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("product_id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        Timestamp createdOn = resultSet.getTimestamp("created_on");


        Product product = Product.builder().
                id(id)
                .name(name)
                .price(price)
                .date(createdOn.toLocalDateTime())
                .build();

        return product;
    }
}
