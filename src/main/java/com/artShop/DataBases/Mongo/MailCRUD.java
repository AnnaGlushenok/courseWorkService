package com.artShop.DataBases.Mongo;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Mail;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MailCRUD implements CRUD<Mail, Iterable<Document>> {

    static {
        MongoDataBase.register(Entity.Mail, new MailCRUD());
    }

    public final String COLLECTION_NAME = "feedbacks";
    private MongoDataBase mongo;

    private MailCRUD() {
        mongo = MongoDataBase.getInstance();
    }

    @Override
    public void insertOne(Mail mail) throws Exception {
        Document info = new Document()
                .append("_id", new ObjectId())
                .append("email", mail.getEmail())
                .append("message", mail.getMessage())
                .append("confirmed", false);

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertOne(info);
    }

    @Override
    public List<Mail> findAll(int limit, int offset) throws Exception {
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        Bson f = Filters.eq("confirmed", false);
        return toList(collection.find(f).skip(offset).limit(limit));
    }

    @Override
    public void updateOne(Mail update, Object id) throws Exception {
        Document updates = new Document()
                .append("confirmed", true);
        Bson setter = new Document("$set", updates);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(Filters.eq("_id", (ObjectId) id), setter);
    }

    @Override
    public void deleteOne(Object id) throws Exception {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.deleteOne(cond);
    }

    @Override
    public List<Mail> toList(Iterable<Document> items) throws Exception {
        ArrayList<Mail> mails = new ArrayList<>();
        items.forEach((Document el) -> {
            mails.add(new Mail(
                    el.getString("email"),
                    el.getString("message"),
                    el.getBoolean("confirmed")
            ));
        });
        return mails;
    }
}
