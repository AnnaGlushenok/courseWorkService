package com.artShop.Interfases.Validation;

import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.Delivery;

public class DeliveryValidate {
    public static String isValid(Delivery delivery) {
        return checkDelivery(delivery);
    }

    private static String checkDelivery(Delivery delivery) {
        return Utils.sendError(delivery);
    }

    public static boolean isValidId(String id) throws NotFoundSuchId {
        return Utils.isValidId(id, "delivery");
    }
}
