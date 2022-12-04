package com.artShop.Mock;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


public class ProductMock {
    IProduct mcollection = (IProduct) new Database().getEntity(Entity.Product);

    ArrayList<Product> products = new ArrayList<>() {{
        add(new Product("category", "34qhejfuu8qfuih", "name", "description", 90, 54));
        add(new Product("category", "8tuwgevq83q384f", "name", "desc", 190, 40));
        add(new Product("category", "38ygw5u3u8qfuh6", "name", "description", 92, 56));
        add(new Product("category", "34yg3673y8qfuih", "name", "big description", 180, 98));
        add(new Product("category", "380987zvx235h71", "name", "little description", 78, 10));
        add(new Product("category", "8u243fyg3f2894t", "name", "Tremendous description", 24, 76));
    }};

    @Before
    public void preTest() throws Exception {
        mcollection = (IProduct) new Database().getEntity(Entity.Product);
        mcollection.insertMany(products);
    }

    @After
    public void afterTest() {
        mcollection = (IProduct) new Database().getEntity(Entity.Product);
    }

    @Test
    public void productFindAllTest() throws CustomException {
        ArrayList<Product> firstTreeProducts = new ArrayList<>() {{
            add(products.get(0));
            add(products.get(1));
            add(products.get(2));
        }};

        ArrayList<Product> productsFromSecondToFive = new ArrayList<>() {{
            add(products.get(2));
            add(products.get(3));
            add(products.get(4));
            add(products.get(5));
        }};
        Assertions.assertArrayEquals(mcollection.findAll(3, 0).toArray(), firstTreeProducts.toArray());
        Assertions.assertArrayEquals(mcollection.findAll(4, 2).toArray(), productsFromSecondToFive.toArray());
    }

    @Test
    public void productInsertOneTest() throws CustomException {
        Product firstProduct = new Product("category", "34gf6y3g6734g63g", "1name", "description", 100, 73);
        Product secondProduct = new Product("category", "34gf6y3g6734g63g", "2name", "description", 48, 48);
        Product thirdProduct = new Product("category", "34gf6y3g6734g63g", "3name", "description", 30, 15);
        ArrayList<Product> updatedProducts = new ArrayList<>() {{
            addAll(products);
        }};
        mcollection.insertOne(firstProduct);
        updatedProducts.add(firstProduct);
        Assertions.assertArrayEquals(mcollection.findAll(7, 0).toArray(), updatedProducts.toArray());
        mcollection.insertOne(secondProduct);
        updatedProducts.add(secondProduct);
        Assertions.assertArrayEquals(mcollection.findAll(8, 0).toArray(), updatedProducts.toArray());
        mcollection.insertOne(thirdProduct);
        updatedProducts.add(thirdProduct);
        Assertions.assertArrayEquals(mcollection.findAll(9, 0).toArray(), updatedProducts.toArray());
    }

    @Test
    public void productInsertManyTest() throws Exception {
        ArrayList<Product> treeProducts = new ArrayList<>() {{
            add(products.get(0));
            add(products.get(1));
            add(products.get(2));
        }};
        ArrayList<Product> newProducts = new ArrayList<>() {
            {
                addAll(products);
                addAll(treeProducts);
            }
        };

        mcollection.insertMany(treeProducts);
        Object[] p = mcollection.findAll(9, 0).toArray();
        Object[] pp = newProducts.toArray();
        Assertions.assertArrayEquals(p, pp);
    }
}
