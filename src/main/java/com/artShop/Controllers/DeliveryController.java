package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.Validation.DeliveryValidate;
import com.artShop.Interfases.Validation.Utils;
import com.artShop.Service.Delivery;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/delivery")
public class DeliveryController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDelivery(HttpServletResponse response, @RequestBody Delivery delivery, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        CRUD table = Strategy.getDataBase().getEntity(Entity.Delivery);
        try {
            String errors = DeliveryValidate.isValid(delivery);
            if (errors.length() != 0)
                return errors;
            table.insertOne(delivery);
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Доставка добавлена";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getDelivery(@RequestParam(defaultValue = "10") String limit, @RequestParam(defaultValue = "0") String offset) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Delivery);
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
    public String updateDelivery(HttpServletResponse response, @PathVariable("id") String id,
                                 @Valid @RequestBody Delivery delivery, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        CRUD table = Strategy.getDataBase().getEntity(Entity.Delivery);
        try {
            String errors = DeliveryValidate.isValid(delivery);
            if (errors.length() != 0)
                return errors;
            if (id.length() == 24)
                table.updateOne(delivery, new ObjectId(id));
            else
                table.updateOne(delivery, Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Возникла проблема с базой данных";
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Неверный запрос";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Доставка обновлена";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDelivery(HttpServletResponse response, @PathVariable("id") String id) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Delivery);
        try {
            if (id.length() == 24)
                table.deleteOne(new ObjectId(id));
            else
                table.deleteOne(Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Возникла проблема с базой данных";
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Неверный запрос";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Доставка удалёна";
    }
}
