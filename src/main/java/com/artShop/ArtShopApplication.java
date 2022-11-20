package com.artShop;

import com.artShop.Interfases.CRUD;
import com.artShop.Service.Delivery;
import com.artShop.Service.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class ArtShopApplication {

    public static void main(String[] args) throws Exception {
        Strategy.initialize();
        SpringApplication.run(ArtShopApplication.class, args);
    }

    @GetMapping("/hello")
    public void hello() throws Exception {
        CRUD prod = Strategy.getDataBase().getEntity("Product");

        ArrayList<Product> pp = new ArrayList<>() {{
            add(new Product("кисти", "cc", "+18748975115", "email@gamili", 1000, 30));
            add(new Product("основа", "C", "+18748975115", "email@gamili", 1000, 30));
        }};

        prod.insertOne(pp.get(0));
    }

    @RequestMapping(value = "/post")
    @PostMapping
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CRUD prod = Strategy.getDataBase().getEntity("Product");
        response.getWriter().println("<p style=\"color: blue\">Success!!!</p>");
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    protected void get(HttpServletResponse response) throws IOException {
        response.getWriter().println("<p style=\"color: blue\">Success doGet()!!!</p>");
    }
}
