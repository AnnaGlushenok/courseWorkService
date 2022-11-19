package com.artShop.Mongo;


public class Product {
    private String category;
    private String productCode;
    private String name;
    private String description;
    private int price;
    private int amount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product(String category, String productCode, String name,
                   String description, int price, int amount) {
        this.category = category;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

}
