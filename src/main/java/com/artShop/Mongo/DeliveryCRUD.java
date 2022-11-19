//package com.artShop.Mongo;
//
//import com.mongodb.client.FindIterable;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.bson.types.ObjectId;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DeliveryCRUD extends MongoDataBase {
//    public final String COLLECTION_NAME = "delivery";
//
//    //mongodb://localhost:27017
//    public DeliveryCRUD(String url, String dataBase) {
//        super(url, dataBase);
//    }
//
//    public FindIterable<Document> findAll() {
//        return super.findAll(COLLECTION_NAME);
//    }
//
//    public FindIterable<Document> findByCriteria(Bson criteria) {//Filters.lt("amount", 100)
//        return super.findByCriteria(COLLECTION_NAME, criteria);
//    }
//
//    public void insert(Delivery delivery) {
//        Document info = new Document()
//                .append("_id", new ObjectId())
//                .append("orders", delivery.getOrders())
//                .append("client", delivery.getClient())
//                .append("telephone", delivery.getTelephone())
//                .append("email", delivery.getEmail())
//                .append("address", delivery.getAddress())
//                .append("dateTime", delivery.getDateTime())
//                .append("confirmed", delivery.getConfirmed());
//        super.insert(COLLECTION_NAME, info);
//    }
//
//    public void insertMany(String fields, ArrayList<Delivery> values) {
//        List<Document> listInfo = new ArrayList<>();
//        for (Delivery d : values)
//            listInfo.add(new Document()
//                    .append("_id", new ObjectId())
//                    .append("orders", d.getOrders())
//                    .append("client", d.getClient())
//                    .append("telephone", d.getTelephone())
//                    .append("email", d.getEmail())
//                    .append("address", d.getAddress())
//                    .append("dateTime", d.getDateTime())
//                    .append("confirmed", d.getConfirmed()));
//
//        super.insertMany(COLLECTION_NAME, listInfo);
//    }
//
//    public void update(Bson cond, ArrayList<String> desiredUpdateField, ArrayList<String> desiredUpdateValue) {
//        //Bson cond = Filters.eq("name", "test");
//        Document updates = new Document();
//        int size = desiredUpdateField.size();
//        for (int i = 0; i < size; i++)
//            updates.append(desiredUpdateField.get(i), desiredUpdateValue.get(i));
//
//        super.update(COLLECTION_NAME, cond, updates);
//    }
//
//    public void deleteOne(String field, String value) {
//        super.deleteOne(COLLECTION_NAME, field, value);
//    }
//}
