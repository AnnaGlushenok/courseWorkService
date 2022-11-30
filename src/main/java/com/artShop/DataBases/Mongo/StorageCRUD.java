package com.artShop.DataBases.Mongo;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Storage;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class StorageCRUD implements CRUD<Storage, Iterable<Document>> {
    static {
        MongoDataBase.register(Entity.Storage, new StorageCRUD());
    }

    public final String COLLECTION_NAME = "storage";
    private final MongoDataBase instance;

    private StorageCRUD() {
        this.instance = MongoDataBase.getInstance();
    }

    public ArrayList<Document> getProducts(List<Storage.Products> products) {
        int size = products.size();
        ArrayList<Document> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new Document()
                    .append("productId", new ObjectId(String.valueOf(products.get(i).getProductId())))
                    .append("amount", products.get(i).getAmount()));
        }

        return list;
    }

    @Override
    public void insertOne(Storage storage) throws Exception {
        Bson cond = Filters.eq("_id", new ObjectId(String.valueOf(storage.getIdStorage())));
        Bson updates = Updates.pushEach("products", getProducts(storage.getProducts()));
        MongoCollection<Document> collection = instance.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(cond, updates);
    }

    @Override
    public List<Storage> findAll(int limit, int offset) throws Exception {
        MongoCollection<Document> collection = instance.getDataBase().getCollection(COLLECTION_NAME);
        return toList(collection.find().skip(offset).limit(limit));
    }

    @Override
    public void updateOne(Storage update, Object objectId) throws Exception {
        ObjectId idProd = new ObjectId(String.valueOf(update.getProducts().get(0).getProductId()));
        Document updates = new Document()
                .append("_id", new ObjectId(String.valueOf(objectId)))
                .append("products", new Document()
                        .append("$elemMatch", new Document()
                                .append("productId", idProd)));

        Bson setter = new Document("$set", new Document().append("products.$.amount", update.getProducts().get(0).getAmount()));
        MongoCollection<Document> collection = instance.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(updates, setter);
    }

    @Override
    public void deleteOne(Object id) throws Exception {
    List<String> ids = (List<String>) id;
        Document updates = new Document().append("_id", new ObjectId(ids.get(0)));
        Bson setter = new Document()
                .append("$pull", new Document()
                        .append("products", new Document()
                                .append("productId", new ObjectId(ids.get(1)))));

        MongoCollection<Document> collection = instance.getDataBase().getCollection(COLLECTION_NAME);
        collection.updateOne(updates, setter);
    }

    @Override
    public List<Storage> toList(Iterable<Document> items) throws Exception {
        ArrayList<Storage> storages = new ArrayList<>();
        items.forEach(el -> {
            ArrayList<Storage.Products> products = new ArrayList<>();
            el.getList("products", Document.class).forEach((Document e) -> {
                products.add(new Storage.Products(e.getObjectId("productId"), e.getInteger("amount")));
            });
            storages.add(new Storage(
                    el.getString("address"),
                    products
            ));
        });

        return storages;
    }
}
