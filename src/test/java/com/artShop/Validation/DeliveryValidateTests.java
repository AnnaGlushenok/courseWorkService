package com.artShop.Validation;

import com.artShop.Interfases.Validation.DeliveryValidate;
import com.artShop.Service.Delivery;
import com.artShop.Service.Order;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryValidateTests {
    @Test
    public void isValidPositiveTest() {
        Order[] order1 = {new Order(5, 5)};
        Order[] order2 = {
                new Order(15, 7),
                new Order(70, 1)
        };
        Delivery delivery1 = new Delivery(order1, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", false);
        Delivery delivery2 = new Delivery(order2, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", true);

        DeliveryValidate.isValid(delivery1);
        DeliveryValidate.isValid(delivery2);
    }

    @Test
    public void isValidNegativeTest() {
        Order[] order1 = {new Order(5, 5)};
        Order[] order2 = {new Order(15, 7)};
        Delivery delivery1 = new Delivery(order1, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", false);
        Delivery delivery2 = new Delivery(order2, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", true);

        DeliveryValidate.isValid(delivery1);
        DeliveryValidate.isValid(delivery2);
    }

    @Test
    public void checkDeliveryTest() {
        Order[] order = {new Order(5, 5)};
        Delivery delivery1 = new Delivery(order, "Name", "+1234567890", "email@gmail.com",
                "г. Гомель, ул. Советская д.46", "2022-02-07 14:55:00", false);


    }

    @Test
    public void isValidIdTest() {

    }
}
