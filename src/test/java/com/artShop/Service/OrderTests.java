package com.artShop.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTests {
    @Test
    public void OrderEntityPositiveTest() {
        Order order1 = new Order(5, 5);
        Order order2 = new Order(16, 15);
        Order order3 = new Order(8, "636d4f8472c6d5729dccbe81");
        Order order4 = new Order(17, "636d4f8472c6d5729dccbe82");

        Assertions.assertEquals(order1.getAmount(), 5);
        Assertions.assertEquals(order1.getProductId(), 5);

        Assertions.assertEquals(order2.getAmount(), 16);
        Assertions.assertEquals(order2.getProductId(), 15);

        Assertions.assertEquals(order3.getAmount(), 8);
        Assertions.assertEquals(order3.getProductId(), "636d4f8472c6d5729dccbe81");

        Assertions.assertEquals(order4.getAmount(), 17);
        Assertions.assertEquals(order4.getProductId(), "636d4f8472c6d5729dccbe82");
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void OrderEntityNegativeTest() {
        Order order = null;
        order.getAmount();
    }
}
