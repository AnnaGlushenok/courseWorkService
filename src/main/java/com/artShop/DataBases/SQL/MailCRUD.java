package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Mail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public void insertOne(Mail mail) throws Exception {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`email`, `message`, `confirmed`) VALUES (?, ?, ?)";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setString(1, mail.getEmail());
        stmt.setString(2, mail.getMessage());
        stmt.setBoolean(3, false);
        stmt.executeUpdate();
    }

    @Override
    public List<Mail> findAll(int limit, int offset) throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + COLLECTION_NAME + " where `confirmed` = 0 limit ? offset ?");
        stmt.setInt(1, limit);
        stmt.setInt(2, offset);
        return toList(stmt.executeQuery());
    }

    @Override
    public void updateOne(Mail update, Object id) throws Exception {
        String query = "UPDATE `feedbacks` SET `confirmed`= ? WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setBoolean(1, true);
        stmt.setInt(2, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public void deleteOne(Object id) throws Exception {
        String query = "DELETE FROM `feedbacks` WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setInt(1, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public List<Mail> toList(ResultSet items) throws Exception {
        ArrayList<Mail> mails = new ArrayList<>();
        while (items.next()) {
            mails.add(new Mail(
                    items.getString(2),
                    items.getString(3)
            ));
        }
        return mails;
    }
}
