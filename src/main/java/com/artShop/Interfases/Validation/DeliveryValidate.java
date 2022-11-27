package com.artShop.Interfases.Validation;

import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NoSuchCategoryException;
import com.artShop.Service.Delivery;
import com.artShop.Service.Order;

public class DeliveryValidate {
    private static final SQLDataBase instance = SQLDataBase.getInstance();

    public static String isValid(Delivery delivery) throws NoSuchCategoryException {
        return checkDelivery(delivery);
    }

    private static String checkDelivery(Delivery delivery) {
        return Utils.sendError(delivery);
    }
}
