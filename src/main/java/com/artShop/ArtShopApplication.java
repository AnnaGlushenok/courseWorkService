package com.artShop;

import com.artShop.DataBases.Strategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ArtShopApplication {
    public static void main(String[] args) throws Exception {
        Strategy.initialize("src/main/resources/application.properties");
        SpringApplication.run(ArtShopApplication.class, args);
    }
}
