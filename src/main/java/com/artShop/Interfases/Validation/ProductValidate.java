package com.artShop.Interfases.Validation;

import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NoSuchCategoryException;
import com.artShop.Service.Product;
import org.springframework.validation.annotation.Validated;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Validated
public class ProductValidate {
    private static final SQLDataBase instance = SQLDataBase.getInstance();

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
