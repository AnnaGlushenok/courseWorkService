package com.artShop.Service;

import java.util.List;

public class Storage {
    private Object idStorage;
    private String address;
    private List<Products> products;

    //productId, amount
    public static class Products {
        private Object productId;
        private int amount;

        public Object getProductId() {
            return productId;
        }

        public int getAmount() {
            return amount;
        }

        public Products(Object productId, int amount) {
            this.productId = productId;
            this.amount = amount;
        }
    }

    public Object getIdStorage() {
        return idStorage;
    }

    public List<Products> getProducts() {
        return products;
    }

    public Storage(String address, List<Products> products) {
        this.address = address;
        this.products = products;
    }
}
