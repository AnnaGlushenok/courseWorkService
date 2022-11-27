package com.artShop.DataBases.Mongo;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ProductCRUD implements IProduct<Product, Iterable<Document>> {

    static {
        MongoDataBase.register(Entity.Product, new ProductCRUD());
    }

    public final String COLLECTION_NAME = "product";
    private MongoDataBase mongo;

    private ProductCRUD() {
        mongo = MongoDataBase.getInstance();
    }

    @Override
    public void insertOne(Product product) throws Exception {
        Document info = new Document()
                .append("_id", new ObjectId())
                .append("category", product.getCategory())
                .append("product_code", product.getProductCode())
                .append("name", product.getName())
                .append("description", product.getDescription())
                .append("price", product.getPrice())
                .append("amount", product.getAmount());

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertOne(info);
    }

    @Override
    public void insertMany(List<Product> products) throws Exception {
        List<Document> listInfo = new ArrayList<>();
        for (Product p : products)
            listInfo.add(new Document()
                    .append("_id", new ObjectId())
                    .append("category", p.getCategory())
                    .append("product_code", p.getProductCode())
                    .append("name", p.getName())
                    .append("description", p.getDescription())
                    .append("price", p.getPrice())
                    .append("amount", p.getAmount()));

        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.insertMany(listInfo);
    }

    @Override
    public List<Product> findAll(int limit, int offset) throws Exception {
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        return toList(collection.find().skip(offset).limit(limit));
    }

    @Override
    public void updateOne(Product newProduct, Object id) {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        Document updates = new Document();
        updates.append("category", newProduct.getCategory())
                .append("product_code", newProduct.getProductCode())
                .append("name", newProduct.getName())
                .append("description", newProduct.getDescription())
                .append("price", newProduct.getPrice())
                .append("amount", newProduct.getAmount());

        Bson setter = new Document("$set", updates);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.findOneAndUpdate(cond, setter);
    }

    @Override
    public void deleteOne(Object id) {
        Bson cond = Filters.eq("_id", (ObjectId) id);
        MongoCollection<Document> collection = mongo.getDataBase().getCollection(COLLECTION_NAME);
        collection.deleteOne(cond);
    }

    @Override
    public List<Product> toList(Iterable<Document> items) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        items.forEach((Document el) -> {
            products.add(new Product(
                    el.getString("category"),
                    el.getString("product_code"),
                    el.getString("name"),
                    el.getString("description"),
                    el.getInteger("price"),
                    el.getInteger("amount")
            ));
        });

        return products;
    }
}
