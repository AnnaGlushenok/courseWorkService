package com.artShop.Controllers;

import com.artShop.DataBases.Entity;
import com.artShop.DataBases.Strategy;
import com.artShop.Interfases.CRUD;
import com.artShop.Interfases.Validation.MailValidate;
import com.artShop.Service.Mail;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/mail")
public class MailController {
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(@RequestBody Mail message, HttpServletResponse response) {
        Mail.sendSimpleMessage(message.getDestination(), message.getSubject(), message.getMessage());
        response.setStatus(HttpServletResponse.SC_OK);
        return "Сообщение отправлено";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @ResponseBody
    public String addFeedback(@RequestBody Mail mail) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Mail);
        try {
            String errors = MailValidate.isValid(mail);
            if (errors.length() != 0)
                return errors;

            table.insertOne(mail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Сообщение добавлено";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object getFeedbacks(@RequestParam(defaultValue = "10") String limit, @RequestParam(defaultValue = "0") String offset) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Mail);
        try {
            return table.findAll(Integer.parseInt(limit), Integer.parseInt(offset));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateProduct(HttpServletResponse response, @PathVariable("id") String id) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Mail);
        try {
            MailValidate.isValidId(id);
            if (id.length() == 24)
                table.updateOne(null, new ObjectId(id));
            else
                table.updateOne(null, Integer.parseInt(id));
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
        return "Статус изменён";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteEmail(HttpServletResponse response, @PathVariable("id") String id) {
        CRUD table = Strategy.getDataBase().getEntity(Entity.Mail);
        try {
            MailValidate.isValidId(id);
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
        return "Сообщение удалёно";
    }
}
