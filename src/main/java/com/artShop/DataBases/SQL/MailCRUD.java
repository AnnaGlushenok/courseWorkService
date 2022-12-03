package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Mail;

import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailCRUD implements CRUD<Mail, ResultSet> {
    static {
        SQLDataBase.register(Entity.Mail, new MailCRUD());
    }

    public static final String COLLECTION_NAME = "feedbacks";
    private final SQLDataBase instance;

    private MailCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    @Override
    public void insertOne(Mail mail) throws CustomException {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`email`, `message`, `confirmed`) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);
            stmt.setString(1, mail.getEmail());
            stmt.setString(2, mail.getMessage());
            stmt.setBoolean(3, false);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Mail> findAll(int limit, int offset) throws CustomException {
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + COLLECTION_NAME + " where `confirmed` = 0 limit ? offset ?");
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            return toList(stmt.executeQuery());
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateOne(Mail update, Object id) throws CustomException {
        String query = "UPDATE `feedbacks` SET `confirmed`= ? WHERE id = ?";
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);
            stmt.setBoolean(1, true);
            stmt.setInt(2, (int) id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteOne(Object id) throws CustomException {
        String query = "DELETE FROM `feedbacks` WHERE id = ?";
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);
            stmt.setInt(1, (int) id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public List<Mail> toList(ResultSet items) throws CustomException {
        ArrayList<Mail> mails = new ArrayList<>();
        try {
            while (items.next()) {
                mails.add(new Mail(
                        items.getString(2),
                        items.getString(3),
                        items.getBoolean(4)
                ));
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return mails;
    }
}
