package com.artShop.Interfases.Validation;

import com.artShop.DataBases.Mongo.MongoDataBase;
import com.artShop.DataBases.SQL.SQLDataBase;
import com.artShop.Exceptions.NoSuchIdException;
import com.artShop.Exceptions.NotFoundSuchId;
import com.artShop.Service.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Utils {
    private static final SQLDataBase instance = SQLDataBase.getInstance();
    private static final MongoDataBase instanceMongo = MongoDataBase.getInstance();

    public static String sendError(BindingResult res) {
        List<ObjectError> allErrors = res.getAllErrors();
        StringBuilder errors = new StringBuilder();
        for (int i = 0; i < res.getErrorCount(); i++) {
            var error = (FieldError) allErrors.get(i);
            errors.append(error.getField()).append(" ").append(allErrors.get(i).getDefaultMessage()).append("\n");
        }
        return errors.toString();
    }

    public static String sendError(Product product) {
        StringBuilder errors = new StringBuilder();
        if (product.getAmount() < 0)
            errors.append("Количество не может быть меньше 0").append("\n");
        if (product.getPrice() < 0)
            errors.append("Цена не может быть меньше 0").append("\n");
        if (product.getCategory().length() == 0)
            errors.append("Длина категории не может быть меньше 0").append("\n");
        if (product.getName().length() == 0)
            errors.append("Длина имени не может быть меньше 0").append("\n");
        if (!product.getProductCode().matches("^[a-z0-9]{8,}$"))
            errors.append("productCode должен содержать не менее 8 символов (латинские буквы и арабские цифры)").append("\n");
        return errors.toString();
    }

    public static String sendError(Delivery delivery) {
        StringBuilder errors = new StringBuilder();
        if (delivery.getOrders().length == 0)
            errors.append("Доставка должна содержать товары").append("\n");
        else
            checkOrders(delivery.getOrders(), errors);
        if (delivery.getClient().length() == 0)
            errors.append("Имя клиента не может быть пустым").append("\n");
        if (delivery.getTelephone().length() == 0)
            errors.append("Телефон клиента не может быть пустым").append("\n");
        if (delivery.getEmail().length() == 0)
            errors.append("Email клиента не может быть пустым").append("\n");
        if (delivery.getAddress().length() == 0)
            errors.append("Адрес клиента не может быть пустым").append("\n");
        if (delivery.getDatetime().length() == 0)
            errors.append("Дата и время не может быть пустым").append("\n");
        return errors.toString();
    }

    public static String sendError(Storage storage) {
        StringBuilder errors = new StringBuilder();
        if (storage.getProducts().size() == 0)
            errors.append("Склад должен содержать товары").append("\n");
        else
            checkProducts(storage.getProducts(), errors);

        return errors.toString();
    }

    public static String sendError(Mail mail) {
        StringBuilder errors = new StringBuilder();
        if (!mail.getEmail().matches("^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            errors.append("Неверный email").append("\n");
        if (mail.getMessage().length() == 0)
            errors.append("Сообщение не может быть пустым").append("\n");
        return errors.toString();
    }

    private static StringBuilder checkProducts(List<Storage.Products> products, StringBuilder errors) {
        int size = products.size();
        if (instance != null) {
            for (int i = 0; i < size; i++) {
                if (((int) products.get(i).getProductId()) < 0) {
                    errors.append("Неверный productId").append("\n");
                    return errors;
                }
                if (products.get(i).getAmount() < 0) {
                    errors.append("Количество продукта не может быть меньше 0").append("\n");
                    return errors;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (products.get(i).getProductId().toString().length() < 24) {
                    errors.append("Неверный productId").append("\n");
                    return errors;
                }
                if (products.get(i).getAmount() < 0) {
                    errors.append("Количество продукта не может быть меньше 0").append("\n");
                    return errors;
                }
            }
        }
        return errors;
    }

    private static StringBuilder checkOrders(Order[] orders, StringBuilder errors) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i].getAmount() < 0) {
                errors.append("Количество продукта не может быть меньше 0").append("\n");
                return errors;
            }
            if (orders[i].getProductId().toString().length() == 0) {
                errors.append("Неверный productId").append("\n");
                return errors;
            }
            ObjectId idMongo;
            int idSql;
            try {
                if (instance == null)
                    idMongo = (ObjectId) orders[i].getProductId();
                else {
                    if (orders[i].getProductId() instanceof String)
                        throw new NoSuchIdException();
                }
            } catch (NumberFormatException e) {
                throw new NoSuchIdException();
            }
        }
        return errors;
    }

    public static boolean isValidId(String id, String collectionName) throws NotFoundSuchId {
        if (instance != null) {
            int i = Integer.parseInt(id);
            try {
                PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + collectionName + " WHERE id=?");
                stmt.setString(1, id);
                ResultSet res = stmt.executeQuery();
                res.next();
                res.getInt(1);
            } catch (SQLException e) {
                throw new NotFoundSuchId();
            }
        } else {
            try {
                MongoCollection<Document> collection = instanceMongo.getDataBase().getCollection(collectionName);
                Bson cond = Filters.eq("_id", new ObjectId(id));
                FindIterable<Document> list = collection.find(cond);
                if (list == null)
                    throw new NotFoundSuchId();
            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }

        return true;
    }
}
