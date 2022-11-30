package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Interfases.IProduct;
import com.artShop.Interfases.Validation.ProductValidate;
import com.artShop.Interfases.Validation.Utils;
import com.artShop.Service.Product;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        IProduct table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            ProductValidate.isValid(product);
            table.insertOne(product);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "er";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт добавлен";
    }

    @RequestMapping(value = "/addMany", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public String addManyProducts(HttpServletResponse response,
                                  @RequestBody List<@Valid Product> products) {
        IProduct<Product, ResultSet> table = (IProduct) Strategy.getDataBase().getEntity(Entity.Product);
        try {
            String errors = ProductValidate.isValid(products);
            if (errors.length() != 0)
                return errors;
            table.insertMany(products);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукты добавлены";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getProducts(@RequestParam(defaultValue = "10") String limit, @RequestParam(defaultValue = "0") String offset) {
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
            ProductValidate.isValid(product);
            ProductValidate.isValidId(id);
            if (id.length() == 24)
                table.updateOne(product, new ObjectId(id));
            else
                table.updateOne(product, Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (NumberFormatException | ClassCastException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Неверный запрос";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            ProductValidate.isValidId(id);
            if (id.length() == 24)
                table.deleteOne(new ObjectId(id));
            else
                table.deleteOne(Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (NumberFormatException | ClassCastException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Неверный запрос";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт удалён";
    }
}
