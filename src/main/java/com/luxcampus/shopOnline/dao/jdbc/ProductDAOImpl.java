package com.luxcampus.shopOnline.dao.jdbc;
import com.luxcampus.shopOnline.dao.ProductDAO;
import com.luxcampus.shopOnline.entity.Product;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
product_id serial PRIMARY KEY,
        name VARCHAR ( 50 ) UNIQUE NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        created_on TIMESTAMP NOT NULL
        );
*/


public class ProductDAOImpl implements ProductDAO {
    private static final ShopRowMapper SHOP_ROW_MAPPER = new ShopRowMapper();
    private static final String GET_SQL = "SELECT product_id, name, price, created_on FROM product;";
    private static final String SAVE_SQL = "INSERT INTO product(name, price, created_on)VALUES (?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM product where product_id= ?";
    private static final String UPDATE_SQL = "UPDATE product SET name = ?, price = ?, date = ? where product_id = ?";

    @Override
    public List<Product> get(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Product> solutions = new ArrayList<>();
            while (resultSet.next()) {
                Product product = SHOP_ROW_MAPPER.mapRow(resultSet);
                solutions.add(product);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Product product) {
        boolean flag;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getDate()));
            preparedStatement.executeUpdate();
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean delete(int id) {
        boolean flag;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    //"UPDATE product SET name = ?, price = ?, date = ? where product_id = ?";
    @Override
    public boolean update(Product product) {
        boolean flag;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getDate()));
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:8200/postgres", "postgres", "postgres");
    }
}
