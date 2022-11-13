package com.artShop.SQL;

import com.artShop.Interfases.DataBase;

import java.sql.*;
import java.util.List;

public class SQLDataBase implements DataBase<ResultSet> {
    private final Connection connection;

    public SQLDataBase(String url) throws SQLException {//jdbc:mysql://localhost/shop
        connection = DriverManager.getConnection(url, "root", "");
    }

    public ResultSet findAll(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery();
    }

    public <T> ResultSet findByCriteria(String collectionName, T criteria) throws SQLException {
        return connection.createStatement().executeQuery();
    }

    public <T> void insert(String collectionName, T values) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute();
    }

    public <T> void insertMany(String collectionName, List<T> values) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeQuery();
    }

    public <T> void update(String collectionName, T desiredField, T desiredValue) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeQuery();
    }

    public void deleteOne(String collectionName, String field, String value) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeQuery();
    }
}
