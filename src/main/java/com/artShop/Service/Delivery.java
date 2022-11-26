package com.artShop.Service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;

public class Delivery {
    private Order[] orders;
    @NotBlank
    @Length(max = 256)
    private String client;
    @NotBlank
    @Length(max = 256)
    private String telephone;
    @NotBlank
    @Length(max = 256)
    private String email;
    @NotBlank
    @Length(max = 256)
    private String address;
    @NotBlank
    @Length(max = 256)
    private String datetime;
    private Boolean confirmed;

    public Order[] getOrders() {
        return orders;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
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
