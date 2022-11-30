package com.artShop.Interfases.Validation;

import com.artShop.DataBases.Mongo.MongoDataBase;
import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.Storage;

import java.util.List;

public class StorageValidate {
    private static final SQLDataBase instanceSQL = SQLDataBase.getInstance();
    private static final MongoDataBase instanceMongo = MongoDataBase.getInstance();

    public static String isValid(Storage storage) {
        return checkStorage(storage);
    }

    public static String isValid(List<Storage> storages) {
        String errors = "";
        int size = storages.size();
        for (int i = 0; i < size; i++) {
            errors = checkStorage(storages.get(i));
            if (errors.length() != 0)
                return errors;
        }

        return errors;
    }

    public static boolean isValidId(String id) throws NotFoundSuchId {
        return Utils.isValidId(id, "product");
    }

    private static String checkStorage(Storage storage) {
        return Utils.sendError(storage);
    }
}
