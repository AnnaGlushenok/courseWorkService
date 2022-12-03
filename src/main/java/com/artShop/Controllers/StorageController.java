package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.Validation.StorageValidate;
import com.artShop.Service.Storage;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addStorageProducts(HttpServletResponse response, @RequestBody Storage storage) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Storage);
        try {
            String errors = StorageValidate.isValid(storage);
            if (errors.length() != 0)
                return errors;
            table.insertOne(storage);
        } catch (CustomException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "er";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Продукт добавлен на склад";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getProducts(@RequestParam(defaultValue = "10") String limit, @RequestParam(defaultValue = "0") String offset) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Storage);
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
    public String updateProduct(HttpServletResponse response, @PathVariable("id") String id, @RequestBody Storage storage) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Storage);
        try {
            StorageValidate.isValid(storage);
            StorageValidate.isValidId(id);
            if (id.length() == 24)
                table.updateOne(storage, new ObjectId(id));
            else
                table.updateOne(storage, Integer.parseInt(id));
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

    @RequestMapping(value = "/delete/{idStorage}/{idProduct}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProduct(HttpServletResponse response, @PathVariable("idStorage") String idStorage,
                                @PathVariable("idProduct") String idProduct) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Storage);
        try {
            StorageValidate.isValidId(idStorage);
            StorageValidate.isValidId(idProduct);
            table.deleteOne(new ArrayList<String>() {{
                add(idStorage);
                add(idProduct);
            }});
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
        return "Продукт удалён со склада";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProduct(HttpServletResponse response, @PathVariable("id") String id) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Storage);
        try {
            StorageValidate.isValidId(id);
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
        return "Продукт удалён со склада";
    }

}
