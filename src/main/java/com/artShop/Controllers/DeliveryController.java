package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Delivery;
import com.artShop.Service.Order;
import com.artShop.Validation.Utils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/delivery")
public class DeliveryController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDelivery(HttpServletResponse response, @Valid @RequestBody Delivery delivery, BindingResult err) {
        if (err.hasErrors())
            return Utils.sendError(err);

        CRUD table = Strategy.getDataBase().getEntity(Entity.Delivery);
        try {
            table.insertOne(delivery);
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
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
            List r = table.findAll(Integer.parseInt(limit), Integer.parseInt(offset));
            return r;
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
//            table.updateOne(delivery, new ObjectId(id));
            table.updateOne(delivery, Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
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
            //  table.deleteOne(new ObjectId(id));
            table.deleteOne(Integer.parseInt(id));
        } catch (SQLException e) {
            response.setStatus(500);
            return "Возникла проблема с базой данных";
        } catch (Exception e) {
            response.setStatus(500);
            return "Неизвестная ошибка";
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "Доставка удалёна";
    }
}
