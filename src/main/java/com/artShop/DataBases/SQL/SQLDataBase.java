package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLDataBase implements DataBase {
    private static SQLDataBase instance;
    private String url;
    private String user;
    private String password;

    private static HashMap<Entity, CRUD> entities = new HashMap<>();

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    public static SQLDataBase getInstance() {
        return instance;
    }

    public static void register(Entity key, CRUD crud) {
        entities.put(key, crud);
    }

    private SQLDataBase(String url, String dataBase, String user, String password) throws SQLException {//jdbc:mysql://localhost/shop
        this.url = url + "/" + dataBase;
        this.user = user;
        this.password = password;
    }

    public static SQLDataBase createInstance(String url, String dataBase, String user, String password) throws SQLException {
        SQLDataBase.instance = new SQLDataBase(url, dataBase, user, password);
        return SQLDataBase.instance;
    }

    @Override
    public CRUD getEntity(Entity key) {
        return entities.get(key);
    }
}
