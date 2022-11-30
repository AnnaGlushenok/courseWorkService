package com.artShop.Interfases.Validation;

import com.artShop.DataBases.Mongo.MongoDataBase;
import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NoSuchCategoryException;
import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.Product;
import org.springframework.validation.annotation.Validated;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Validated
public class ProductValidate {
    private static final SQLDataBase instance = SQLDataBase.getInstance();
    private static final MongoDataBase instanceMongo = MongoDataBase.getInstance();

    public static void isValid(Product product) throws NoSuchCategoryException {
        if (instance != null)
            checkCategory(product);
    }

    public static String isValid(List<Product> products) throws NoSuchCategoryException {
        String errors = "";
        try {
            for (Product p : products) {
                errors = Utils.sendError(p);
                if (errors.length() != 0)
                    return errors;
                if (instance != null)
                    checkCategory(p);
            }
        } catch (SQLException e) {
            throw new NoSuchCategoryException(e);
        }
        return errors;
    }

    public static boolean isValidId(String id) throws NotFoundSuchId {
        return Utils.isValidId(id, "product");
    }

    private static void checkCategory(Product product) throws NoSuchCategoryException {
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT id FROM `categories` WHERE `name`=?");
            stmt.setString(1, product.getCategory());
            ResultSet res = stmt.executeQuery();
            res.next();
            res.getInt(1);
        } catch (SQLException e) {
            throw new NoSuchCategoryException(e);
        }
    }
}
