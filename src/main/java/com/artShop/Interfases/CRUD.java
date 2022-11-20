package com.artShop.Interfases;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T,R> {
    void insertOne(T item) throws Exception;

    void insertMany(List<T> items) throws Exception;

    List<T> findAll() throws Exception;

    void updateOne(T update, Object id) throws Exception;

    void deleteOne(Object id) throws Exception;

    //Iterable<Document>, ResultSet
    List<T> toList(R items) throws SQLException;
}
