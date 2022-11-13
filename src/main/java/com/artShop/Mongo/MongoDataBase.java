package com.artShop.Mongo;

import com.artShop.Interfases.DataBase;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class MongoDataBase implements DataBase<FindIterable> {
    //что делать с вставкой цифровых значений?
    //TODO ограничить видимость методов
    private String url;
    private MongoClient mongo;
    private MongoDatabase dataBase;

    public MongoDataBase(String url, String dataBase) {
        this.url = url;
        this.dataBase = connect(dataBase);
    }

    private MongoDatabase connect(String dataBase) {
        //String url = "mongodb://localhost:27017";
        mongo = MongoClients.create(url);
        MongoDatabase db = mongo.getDatabase(dataBase);

        return db;
    }

    public void close() {
        mongo.close();
    }

    public FindIterable<Document> findAll(String collectionName) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        return collection.find();
    }

    public <T> FindIterable<Document> findByCriteria(String collectionName, T criteria) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        return collection.find((Bson) criteria);
    }

    public <T> void insert(String collectionName, T values) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        collection.insertOne((Document) values);
    }

    public <T> void insertMany(String collectionName, List<T> values) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        collection.insertMany((List<Document>) values);
    }

    public <T> void update(String collectionName, T cond, T updates) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        Bson update = new Document("$set", updates);

        Document d = collection.findOneAndUpdate((Bson) cond, update);
    }

    public void deleteOne(String collectionName, String field, String value) {
        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
        Bson query = Filters.eq(field, value);
        collection.deleteOne(query);
    }

//    public <T> void deleteMany(String collectionName, String field, String value) {
//        MongoCollection<Document> collection = dataBase.getCollection(collectionName);
//        Bson query = Filters.all(field, value);
//        collection.deleteMany(query);
//    }
}
