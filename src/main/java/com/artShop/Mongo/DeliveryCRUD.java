package com.artShop.Mongo;

import com.artShop.Interfases.CRUD;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.attribute.standard.OrientationRequested;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryCRUD implements CRUD<Delivery, Iterable<Document>> {

    static {
        MongoDataBase.register("Delivery", new DeliveryCRUD());
    }

    public final String COLLECTION_NAME = "delivery";
    private MongoDataBase mongo;

    private DeliveryCRUD() {
        mongo = MongoDataBase.getInstance();
    }

    @Override
    public void insertOne(Delivery delivery) throws Exception {
        Document info = new Document()
                .append("_id", new ObjectId())
                .append("orders", delivery.getOrders())
                .append("client", delivery.getClient())
                .append("telephone", delivery.getTelephone())
                .append("email", delivery.getEmail())
                .append("address", delivery.getAddress())
                .append("dateTime", delivery.getDateTime())
                .append("confirmed", delivery.getConfirmed());

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertOne(info);
    }

    @Override
    public void insertMany(List<Delivery> items) throws Exception {
        List<Document> listInfo = new ArrayList<>();
        for (Delivery d : items)
            listInfo.add(new Document()
                    .append("_id", new ObjectId())
                    .append("orders", d.getOrders())
                    .append("client", d.getClient())
                    .append("telephone", d.getTelephone())
                    .append("email", d.getEmail())
                    .append("address", d.getAddress())
                    .append("dateTime", d.getDateTime())
                    .append("confirmed", d.getConfirmed()));

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertMany(listInfo);
    }

    @Override
    public List<Delivery> findAll() throws Exception {
        MongoCollection<Document> client = mongo.getDataBase().getCollection(COLLECTION_NAME);
        return toList(client.find());
    }

    @Override
    public void updateOne(Delivery newDelivery, Object id) throws Exception {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        Document updates = new Document();
        updates.append("orders", newDelivery.getOrders())
                .append("client", newDelivery.getClient())
                .append("telephone", newDelivery.getTelephone())
                .append("email", newDelivery.getEmail())
                .append("address", newDelivery.getAddress())
                .append("dateTime", newDelivery.getDateTime())
                .append("confirmed", newDelivery.getConfirmed());

        Bson setter = new Document("$set", updates);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(cond, setter);
    }

    @Override
    public void deleteOne(Object id) throws Exception {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.deleteOne(cond);
    }

    @Override
    public List<Delivery> toList(Iterable<Document> items) throws SQLException {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        items.forEach((Document el) -> {
            ArrayList<Order> orders = new ArrayList<>();
            el.getList("orders", Document.class).forEach((Document e) -> {
                orders.add(new Order(e.getInteger("amount"),
                        e.getObjectId("id_product")));
            });
            deliveries.add(new Delivery(
                    orders.toArray(Order[]::new),
                    el.getString("client"),
                    el.getString("telephone"),
                    el.getString("email"),
                    el.getString("address"),
                    el.getString("dateTime"),
                    el.getBoolean("confirmed")
            ));
        });

        return deliveries;
    }
}
