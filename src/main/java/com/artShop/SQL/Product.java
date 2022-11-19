package com.artShop.SQL;

public class Product {
    private String productCode;
    private String name;
    private String description;
    private double price;
    private int amount;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String product_code) {
        this.productCode = product_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product(String productCode, String name,
                   String description, double price, int amount) {
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }
}

