package com.artShop.Service;

public class Order {
    private int amount;
    private Object productId;

    public int getAmount() {
        return amount;
    }

    public Object getProductId() {
        return productId;
    }

    public Order() {
    }

    public Order(int amount, Object productId) {
        this.amount = amount;
        this.productId = productId;
    }
}
