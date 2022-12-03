package com.artShop.Mock;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.DataBase;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database implements DataBase {
    private static HashMap<Entity, CRUD> entities = new HashMap<>() {{
        put(Entity.Product, new ProductTest());
    }};

    @Override
    public CRUD getEntity(Entity key) {
        return entities.get(key);
    }

    private static class ProductTest implements IProduct<Product, Object> {
        List<Product> products = new ArrayList<>();

        @Override
        public void insertOne(Product product) throws CustomException {
            products.add(product);
        }

        @Override
        public List<Product> findAll(int limit, int offset) throws CustomException {
            return products.stream().skip(offset).limit(limit).toList();
        }

        @Override
        public void updateOne(Product update, Object id) throws CustomException {
            int size = products.size();
            int index = -1;
            for (int i = 0; i < size; i++) {
                if (products.get(i).getId().equals(id)) {
                    index = i;
                    break;
                }
            }

            if (index == -1)
                throw new CustomException("Не найден продукт", HttpServletResponse.SC_BAD_REQUEST);

            products.set(index, update);
        }

        @Override
        public void deleteOne(Object id) throws CustomException {
            int size = products.size();
            int index = -1;
            for (int i = 0; i < size; i++) {
                if (products.get(i).getId().equals(id)) {
                    index = i;
                    break;
                }
            }

            if (index == -1)
                throw new CustomException("Не найден продукт", HttpServletResponse.SC_BAD_REQUEST);

            products.remove(index);
        }

        @Override
        public void insertMany(List<Product> products) throws Exception {
            this.products.addAll(products);
        }
    }
}


