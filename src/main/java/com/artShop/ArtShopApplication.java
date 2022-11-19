package com.artShop;

import com.artShop.Interfases.CRUD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RestController
public class ArtShopApplication {

    public static void main(String[] args) throws Exception {
        Strategy.initialize();
        SpringApplication.run(ArtShopApplication.class, args);
    }

    @GetMapping("/hello")
    public List hello() throws Exception {
        CRUD prod = Strategy.getDataBase().getEntity("Product");

        return prod.findAll();
    }

    @RequestMapping(value = "/post")
    @PostMapping
    public void post(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().println("<p style=\"color: blue\">Success!!!</p>");
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    protected void get(HttpServletResponse response) throws IOException {
        response.getWriter().println("<p style=\"color: blue\">Success doGet()!!!</p>");
    }
}
