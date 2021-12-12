package com.luxcampus.shopOnline.dao.jdbc.mapper;

import com.luxcampus.shopOnline.dao.jdbc.ProductRowMapper;
import com.luxcampus.shopOnline.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // prepare
        ProductRowMapper shopMapper = new ProductRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 11, 19, 22, 30);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("product_id")).thenReturn(100);
        when(resultSetMock.getString("name")).thenReturn("Milk");
        when(resultSetMock.getDouble("price")).thenReturn(100.00);
        when(resultSetMock.getTimestamp("created_on")).thenReturn(timestamp);

        // when
        Product actual = shopMapper.mapRow(resultSetMock);

        // then
        //assertEquals(100, actual.getId());
        assertEquals("Milk", actual.getName());
        assertEquals(100.00, actual.getPrice());
        assertEquals(localDateTime, actual.getCreated_on());
    }
}