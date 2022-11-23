package com.artShop.Service;

public class Order {
    private int amount;
    private Object productId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public Order(int amount, Object productId) {
        this.amount = amount;
        this.productId = productId;
    }
}
