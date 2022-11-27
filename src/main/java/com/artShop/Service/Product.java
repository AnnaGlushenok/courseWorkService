package com.artShop.Service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Product {
    @NotBlank
    @Length(max = 256)
    private String category;
    @NotBlank
    @Length(max = 256)
    @Pattern(regexp = "^[a-z0-9]{8,}$",
            message = "должен содержать не менее 8 символов (латинские буквы и арабские цифры)")
    private String productCode;
    @NotBlank
    @Length(max = 256)
    private String name;
    @NotBlank
    @Length(max = 256)
    private String description;
    @Min(0)
    private int price;
    @Min(0)
    private int amount;

    public String getCategory() {
        return category;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
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
