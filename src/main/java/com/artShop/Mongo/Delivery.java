package com.artShop.Mongo;

import org.bson.types.ObjectId;

import java.util.HashMap;

public class Delivery {    //id_Product ?
    private HashMap<ObjectId, Integer> orders;
    private String client;
    private String telephone;
    private String email;
    private String address;
    private String dateTime;
    private Boolean confirmed;

    public HashMap<ObjectId, Integer> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<ObjectId, Integer> orders) {
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

    public Delivery(HashMap<ObjectId, Integer> orders, String client, String telephone, String email, String address, String dateTime, Boolean confirmed) {
        this.orders = orders;
        this.client = client;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.dateTime = dateTime;
        this.confirmed = confirmed;
    }
}
