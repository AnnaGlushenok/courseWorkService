package com.artShop.Interfases;

import java.util.List;

public interface DataBase<R> {

    R findAll(String collectionName) throws Exception;

    <T> R findByCriteria(String collectionName, T criteria) throws Exception;

    <T> void insert(String collectionName, T values) throws Exception;

    <T> void insertMany(String collectionName, List<T> values) throws Exception;

    <T> void update(String collectionName, T cond, T updates) throws Exception;

    void deleteOne(String collectionName, String field, String value) throws Exception;

    //зачем вообще удалять много
    //void deleteMany(String collectionName, String field, String value);
}
