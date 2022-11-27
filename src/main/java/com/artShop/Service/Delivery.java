package com.artShop.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class Delivery {
    private Order[] orders;
    private String client;
    private String telephone;
    private String email;
    private String address;
    private String datetime;
    private Boolean confirmed;

    public Order[] getOrders() {
        return orders;
    }

    public String getClient() {
        return client;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getDatetime() {
        return datetime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public ArrayList<Order> getListOrders() {
        ArrayList<Order> listOrders = new ArrayList<>();
        listOrders.addAll(Arrays.asList(orders));
        return listOrders;
    }

    public Delivery(Order[] orders, String client, String telephone, String email,
                    String address, String dateTime, Boolean confirmed) {
        this.orders = orders;
        this.client = client;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.datetime = dateTime;
        this.confirmed = confirmed;
    }
}
