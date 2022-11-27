package com.artShop.DataBases.Mongo;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.DataBase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.HashMap;

public class MongoDataBase implements DataBase {
    private String url;
    private MongoClient mongo;
    private MongoDatabase dataBase;
    private static MongoDataBase instance;
    private static HashMap<Entity, CRUD> entities = new HashMap<>();


    public static MongoDataBase getInstance() {
        return instance;
    }

    public MongoDatabase getDataBase() {
        return dataBase;
    }

    private MongoDataBase(String url, String dataBase) {
        this.url = url;
        this.dataBase = connect(dataBase);
    }

    public static void register(Entity key, CRUD crud) {
        entities.put(key, crud);
    }

    public static MongoDataBase createInstance(String url, String dataBase) {
        instance = new MongoDataBase(url, dataBase);
        return instance;
    }

    private MongoDatabase connect(String dataBase) {
        mongo = MongoClients.create(url);
        return mongo.getDatabase(dataBase);
    }

    public void close() {
        mongo.close();
    }

    @Override
    public CRUD getEntity(Entity key) {
        return entities.get(key);
    }
}
