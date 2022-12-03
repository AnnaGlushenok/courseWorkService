package com.artShop.Service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class StorageTests {
    @Test
    public void storageEntityPositiveTest() {
        ArrayList<Storage.Products> prod = new ArrayList<>() {{
            add(new Storage.Products(1, 5));
            add(new Storage.Products(5, 15));
            add(new Storage.Products(9, 71));
        }};
        Storage storage1 = new Storage("address", prod);

        Assertions.assertEquals(storage1.getProducts().get(0).getProductId(), 1);
        Assertions.assertEquals(storage1.getProducts().get(1).getProductId(), 5);
        Assertions.assertEquals(storage1.getProducts().get(2).getProductId(), 9);
        Assertions.assertEquals(storage1.getProducts().get(0).getAmount(), 5);
        Assertions.assertEquals(storage1.getProducts().get(1).getAmount(), 15);
        Assertions.assertEquals(storage1.getProducts().get(2).getAmount(), 71);
        Assertions.assertArrayEquals(storage1.getProducts().toArray(), prod.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void storageEntityNegativeTest() {
        ArrayList<Storage.Products> prod = null;
        Storage storage1 = new Storage("address", prod);
        storage1.getProducts().get(0).getProductId();
    }
}
