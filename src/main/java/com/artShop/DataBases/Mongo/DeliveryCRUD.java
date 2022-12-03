package com.artShop.DataBases.Mongo;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Delivery;
import com.artShop.Service.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryCRUD implements CRUD<Delivery, Iterable<Document>> {

    static {
        MongoDataBase.register(Entity.Delivery, new DeliveryCRUD());
    }

    public final String COLLECTION_NAME = "delivery";
    private MongoDataBase mongo;

    private DeliveryCRUD() {
        mongo = MongoDataBase.getInstance();
    }

    public ArrayList<Document> getOrders(Order[] orders) {
        ArrayList<Document> list = new ArrayList<>(orders.length);
        for (Order o : orders)
            list.add(new Document()
                    .append("id_product", new ObjectId(o.getProductId().toString()))
                    .append("amount", o.getAmount()));

        return list;
    }

    @Override
    public void insertOne(Delivery delivery) throws CustomException {
        Document info = new Document()
                .append("_id", new ObjectId())
                .append("orders", getOrders(delivery.getOrders()))
                .append("client", delivery.getClient())
                .append("telephone", delivery.getTelephone())
                .append("email", delivery.getEmail())
                .append("address", delivery.getAddress())
                .append("datetime", delivery.getDatetime())
                .append("confirmed", delivery.getConfirmed());

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertOne(info);
    }

    @Override
    public List<Delivery> findAll(int limit, int offset) throws CustomException {
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        return toList(collection.find().skip(offset).limit(limit));
    }

    @Override
    public void updateOne(Delivery newDelivery, Object id) throws CustomException {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        Document updates = new Document().append("orders", getOrders(newDelivery.getOrders()))
                .append("client", newDelivery.getClient())
                .append("telephone", newDelivery.getTelephone())
                .append("email", newDelivery.getEmail())
                .append("address", newDelivery.getAddress())
                .append("datetime", newDelivery.getDatetime())
                .append("confirmed", newDelivery.getConfirmed());

        Bson setter = new Document("$set", updates);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(cond, setter);
    }

    @Override
    public void deleteOne(Object id) throws CustomException {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.deleteOne(cond);
    }

    public ArrayList<Delivery> toList(Iterable<Document> items) throws CustomException {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        items.forEach((Document el) -> {
            ArrayList<Order> orders = new ArrayList<>();
            el.getList("orders", Document.class).forEach((Document e) -> {
                orders.add(new Order(e.getInteger("amount"),
                        e.getObjectId("id_product").toString()));
            });
            deliveries.add(new Delivery(
                    orders.toArray(Order[]::new),
                    el.getString("client"),
                    el.getString("telephone"),
                    el.getString("email"),
                    el.getString("address"),
                    el.getString("datetime"),
                    el.getBoolean("confirmed")
            ));
        });

        return deliveries;
    }
}
