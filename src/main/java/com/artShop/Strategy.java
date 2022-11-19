package com.artShop;

import com.artShop.Interfases.DataBase;
import com.artShop.Mongo.MongoDataBase;
import com.artShop.SQL.SQLDataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Strategy {
    private static DataBase dataBase;//SQL or Mongo

    public static DataBase getDataBase() {
        return dataBase;
    }


    public static void initialize() throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switch (props.getProperty("USE_DATABASE")) {
            case "MONGODB" -> {
                dataBase = MongoDataBase.createInstance(props.getProperty("MONGODB_URL"), props.getProperty("MONGODB_DATABASE"));
                Class.forName("com.artShop.Mongo.ProductCRUD");
            }
            case "MYSQL" -> {
                dataBase = SQLDataBase.createInstance(props.getProperty("MYSQL_URL"), props.getProperty("MYSQL_DATABASE"),
                        props.getProperty("MYSQL_USERNAME"), props.getProperty("MYSQL_PASSWORD"));
                Class.forName("com.artShop.SQL.ProductCRUD");
            }
            default -> throw new Exception("No such Database");
        }
    }
}
