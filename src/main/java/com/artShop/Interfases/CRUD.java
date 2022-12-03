package com.artShop.Interfases;

import com.artShop.Exceptions.CustomException;

import java.util.List;

public interface CRUD<T, R> {
    void insertOne(T item) throws CustomException;

    List<T> findAll(int limit, int offset) throws CustomException;

    void updateOne(T update, Object id) throws CustomException;

    void deleteOne(Object id) throws CustomException;
}
