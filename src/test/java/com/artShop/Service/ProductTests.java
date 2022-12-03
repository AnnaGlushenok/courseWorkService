package com.artShop.Service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTests {
    @Test
    public void productEntityPositiveTest() {
        Product prod1 = new Product("category", "34qhejfuu8qfuih", "name", "description", 90, 54);
        Product prod2 = new Product(null, null, null, null, 0, 54);

        Assertions.assertEquals(prod1.getCategory(), "category");
        Assertions.assertEquals(prod1.getProductCode(), "34qhejfuu8qfuih");
        Assertions.assertEquals(prod1.getName(), "name");
        Assertions.assertEquals(prod1.getDescription(), "description");
        Assertions.assertEquals(prod1.getPrice(), 90);
        Assertions.assertEquals(prod1.getAmount(), 54);

        Assertions.assertNull(prod2.getCategory());
        Assertions.assertNull(prod2.getProductCode());
        Assertions.assertNull(prod2.getName());
        Assertions.assertNull(prod2.getDescription());
        Assertions.assertEquals(prod2.getPrice(), 0);
        Assertions.assertEquals(prod2.getAmount(), 54);
    }

    @Test(expected = NullPointerException.class)
    public void productEntityNegativeTest() {
        Product prod = null;

        prod.getProductCode();
    }
}
