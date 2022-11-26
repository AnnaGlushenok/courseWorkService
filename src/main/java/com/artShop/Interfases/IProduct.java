package com.artShop.Interfases;

import com.artShop.Service.Product;

import java.util.List;

public interface IProduct<T, R> extends CRUD<T, R> {
    void insertMany(List<Product> products) throws Exception;
}
