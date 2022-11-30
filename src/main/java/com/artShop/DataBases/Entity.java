package com.artShop.DataBases;

public enum Entity {
    Product("Product"),
    Delivery("Delivery"),
    Category("Category"),
    Storage("Storage"),
    Mail("Mail");

    private final String name;

    public String getName() {
        return name;
    }

    Entity(String name) {
        this.name = name;
    }
}
