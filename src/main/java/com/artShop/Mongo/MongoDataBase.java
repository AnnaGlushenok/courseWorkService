package com.artShop.Mongo;

import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.DataBase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.HashMap;

public class MongoDataBase implements DataBase {
    //что делать с вставкой цифровых значений?
    private String url;
    private MongoClient mongo;
    private MongoDatabase dataBase;
    private static MongoDataBase instance;

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

    private static HashMap<String, CRUD> entities = new HashMap<>();

    public static void register(String key, CRUD crud) {
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
    public CRUD getEntity(String key) {
        return entities.get(key);
    }
}
