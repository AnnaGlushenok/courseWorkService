package com.artShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RestController
public class ArtShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtShopApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        String p = "products";

        return String.format("Hello %s!", name);
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
