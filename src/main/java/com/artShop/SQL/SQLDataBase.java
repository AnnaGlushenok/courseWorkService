package com.artShop.SQL;

import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLDataBase implements DataBase {
    private Connection connection;
    private static SQLDataBase instance;

    public Connection getConnection() {
        return connection;
    }

    public static SQLDataBase getInstance() {
        return instance;
    }

    private static HashMap<String, CRUD> entities = new HashMap<>();

    public static void register(String key, CRUD crud) {
        entities.put(key, crud);
    }


    public void close() throws SQLException {
        connection.close();
    }

    private SQLDataBase(String url, String dataBase, String user, String password) throws SQLException {//jdbc:mysql://localhost/shop
        connection = DriverManager.getConnection(url + "/" + dataBase, user, password);
    }

    public static SQLDataBase createInstance(String url, String dataBase, String user, String password) throws SQLException {
        SQLDataBase.instance = new SQLDataBase(url, dataBase, user, password);
        return SQLDataBase.instance;
    }

    @Override
    public CRUD getEntity(String key) {
        return entities.get(key);
    }
}
