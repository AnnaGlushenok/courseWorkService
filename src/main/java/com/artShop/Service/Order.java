package com.artShop.Service;

import javax.validation.constraints.Min;

public class Order {
    @Min(0)
    private int id;
    @Min(0)
    private int amount;

    private Object productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    public Order() {

    }

    public Order(int amount, Object productId) {
        this.amount = amount;
        this.productId = productId;
    }
}
