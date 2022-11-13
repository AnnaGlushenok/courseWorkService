package com.artShop.Mongo;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ProductCRUD extends MongoDataBase {
    public final String COLLECTION_NAME = "products";

    public ProductCRUD(String url, String dataBase) {
        super(url, dataBase);
    }

    public FindIterable<Document> findAll() {
        return super.findAll(COLLECTION_NAME);
    }

    public FindIterable<Document> findByCriteria(Bson criteria) {//Filters.lt("amount", 100)
        return super.findByCriteria(COLLECTION_NAME, criteria);
    }

    public void insert(Products product) {
        Document info = new Document()
                .append("_id", new ObjectId())
                .append("category", product.getCategory())
                .append("product_code", product.getProductCode())
                .append("name", product.getName())
                .append("description", product.getDescription())
                .append("price", product.getPrice())
                .append("amount", product.getAmount());
        super.insert(COLLECTION_NAME, info);
    }

    public void insertMany(String fields, ArrayList<Products> values) {
        List<Document> listInfo = new ArrayList<>();
        for (Products p : values)
            listInfo.add(new Document()
                    .append("_id", new ObjectId())
                    .append("category", p.getCategory())
                    .append("product_code", p.getProductCode())
                    .append("name", p.getName())
                    .append("description", p.getDescription())
                    .append("price", p.getPrice())
                    .append("amount", p.getAmount()));

        super.insertMany(COLLECTION_NAME, listInfo);
    }

    public void update(Bson cond, ArrayList<String> desiredUpdateField, ArrayList<String> desiredUpdateValue) {
        //Bson cond = Filters.eq("name", "test");
        Document updates = new Document();
        int size = desiredUpdateField.size();
        for (int i = 0; i < size; i++)
            updates.append(desiredUpdateField.get(i), desiredUpdateValue.get(i));

        super.update(COLLECTION_NAME, cond, updates);
    }

    public void deleteOne(String field, String value) {
        super.deleteOne(COLLECTION_NAME, field, value);
    }
}
