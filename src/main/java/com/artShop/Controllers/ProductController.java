package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;
import com.artShop.Validation.Utils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addProduct(HttpServletResponse response,
                             @Valid @RequestBody Product product, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        IProduct<Product, ResultSet> table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            table.insertOne(product);
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт добавлен";
    }

    @RequestMapping(value = "/addMany", method = RequestMethod.POST)
    @ResponseBody
    public String addManyProduct(HttpServletResponse response,
                                 @Valid @RequestBody List<Product> products, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        IProduct<Product, ResultSet> table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            table.insertMany(products);
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукты добавлены";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getProducts(@RequestParam(defaultValue = "10") String limit, @RequestParam(defaultValue = "0") String offset,
                              HttpServletRequest request, HttpServletResponse response) {
        IProduct table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            return table.findAll(Integer.parseInt(limit), Integer.parseInt(offset));
        } catch (NumberFormatException e) {
            return "Неверный параметр limit и/или offset";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateProduct(HttpServletResponse response, @PathVariable("id") String id,
                                @Valid @RequestBody Product product, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        IProduct<Product, ResultSet> table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            table.updateOne(product, new ObjectId(id));
//            database.updateOne(product, Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт обновлён";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProduct(HttpServletResponse response, @PathVariable("id") String id) {
        IProduct<Product, ResultSet> table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            table.deleteOne( new ObjectId(id));
//            database.deleteOne(Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт удалён";
    }
}
