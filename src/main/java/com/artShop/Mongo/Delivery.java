package com.artShop.Mongo;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Delivery {
    private Order[] orders;
    private String client;
    private String telephone;
    private String email;
    private String address;
    private String dateTime;
    private Boolean confirmed;

    public ArrayList<Document> getOrders() {
        ArrayList<Document> list = new ArrayList<>(orders.length);
        for (Order o : orders)
            list.add(new Document()
                    .append("object_id", o.getObjectId())
                    .append("amount", o.getAmount()));

        return list;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Delivery(Order[] orders, String client, String telephone, String email,
                    String address, String dateTime, Boolean confirmed) {
        this.orders = orders;
        this.client = client;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.dateTime = dateTime;
        this.confirmed = confirmed;
    }
}
