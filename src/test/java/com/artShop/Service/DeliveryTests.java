package com.artShop.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryTests {
    @org.junit.Test
    public void deliveryEntityPositiveTest() {
        Order[] order1 = {new Order(5, 5)};
        Order[] order2 = {
                new Order(5, 5),
                new Order(10, 2)
        };

        Delivery delivery1 = new Delivery(order1, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", false);
        Delivery delivery2 = new Delivery(order2, "client", "+7895106548976", "ebwvermyl@gmail.com",
                "г. Витебск, ул. Интернацоналистов д. 23, кв. 70", "2022-11-29 11:10:00", true);

        Assertions.assertEquals(delivery1.getClient(), "Name");
        Assertions.assertEquals(delivery1.getTelephone(), "+1234567890");
        Assertions.assertEquals(delivery1.getEmail(), "email@gmail.com");
        Assertions.assertEquals(delivery1.getAddress(), "г. Гомель, ул. Советская д.46");
        Assertions.assertEquals(delivery1.getDatetime(), "2022-02-07 14:55:00");
        Assertions.assertFalse(delivery1.getConfirmed());
        Assertions.assertArrayEquals(delivery1.getListOrders().toArray(), order1);

        Assertions.assertEquals(delivery2.getClient(), "client");
        Assertions.assertEquals(delivery2.getTelephone(), "+7895106548976");
        Assertions.assertEquals(delivery2.getEmail(), "ebwvermyl@gmail.com");
        Assertions.assertEquals(delivery2.getAddress(), "г. Витебск, ул. Интернацоналистов д. 23, кв. 70");
        Assertions.assertEquals(delivery2.getDatetime(), "2022-11-29 11:10:00");
        Assertions.assertTrue(delivery2.getConfirmed());
        Assertions.assertArrayEquals(delivery2.getListOrders().toArray(), order2);
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void deliveryEntityNegativeTest() {
        Order[] order = {null, null};
        Delivery delivery = new Delivery(order, null, null, null,
                null, null, false);
        Assertions.assertNull(delivery.getClient());
        Assertions.assertNull(delivery.getTelephone());
        Assertions.assertNull(delivery.getEmail());
        Assertions.assertNull(delivery.getAddress());
        Assertions.assertNull(delivery.getDatetime());
        Assertions.assertFalse(delivery.getConfirmed());
        delivery.getOrders()[0].getAmount();
    }
}
