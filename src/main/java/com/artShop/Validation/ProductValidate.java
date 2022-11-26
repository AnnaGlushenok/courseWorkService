package com.artShop.Validation;

import com.artShop.Service.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public class ProductValidate {
    public static ResponseEntity validate(@Valid Product product){
        return ResponseEntity.ok("valid");
    }
}
