package com.artShop.Mongo;

import org.bson.types.ObjectId;

public class Order {
    private int amount;
    private ObjectId objectId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Order(int amount, ObjectId objectId) {
        this.amount = amount;
        this.objectId = objectId;
    }
}
