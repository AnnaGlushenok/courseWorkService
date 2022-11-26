package com.artShop.Interfases;

import java.util.List;

public interface CRUD<T,R> {
    void insertOne(T item) throws Exception;

    List<T> findAll(int limit, int offset) throws Exception;

    void updateOne(T update, Object id) throws Exception;

    void deleteOne(Object id) throws Exception;

    //Iterable<Document>, ResultSet
    List<T> toList(R items) throws Exception;
}
