package com.artShop.Interfases.Validation;

import com.artShop.DataBases.Mongo.MongoDataBase;
import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.Mail;

public class MailValidate {
    private static final SQLDataBase instanceSQL = SQLDataBase.getInstance();
    private static final MongoDataBase instanceMongo = MongoDataBase.getInstance();

    public static String isValid(Mail mail) {
        return checkMail(mail);
    }

    public static boolean isValidId(String id) throws NotFoundSuchId {
        return Utils.isValidId(id, "product");
    }
    private static String checkMail(Mail mail){
        return Utils.sendError(mail);
    }
}
