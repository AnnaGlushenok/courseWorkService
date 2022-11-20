package com.artShop.Service;

public class Order {
    private int amount;
    private Object id;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Object getId() {
        return id;
    }

    public void setObjectId(Object id) {
        this.id = id;
    }

    public Order(int amount, Object id) {
        this.amount = amount;
        this.id = id;
    }
}
