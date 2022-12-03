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
        add(new Product("category", "38ygw5u3u8qfuih", "name", "description", 90, 54));
    }};

    @Before
    public void preTest() throws Exception {
        mcollection.insertMany(products);
    }

    @After
    public void afterTest() {
        mcollection = (IProduct) new Database().getEntity(Entity.Product);
    }

    @Test
    public void n() throws CustomException {
        Assertions.assertArrayEquals(mcollection.findAll(5, 0).toArray(), products.toArray());
    }
}
