package com.artShop.Interfases.Validation;

import com.artShop.DataBases.Mongo.MongoDataBase;
import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NoSuchCategoryException;
import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.Delivery;

public class DeliveryValidate {
    private static final SQLDataBase instance = SQLDataBase.getInstance();
    private static final MongoDataBase instanceMongo = MongoDataBase.getInstance();

    public static String isValid(Delivery delivery) throws NoSuchCategoryException {
        return checkDelivery(delivery);
    }

    private static String checkDelivery(Delivery delivery) {
        return Utils.sendError(delivery);
    }

    public static boolean isValidId(String id) throws NotFoundSuchId {
        return Utils.isValidId(id, "delivery");
    }
}
