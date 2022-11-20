package com.artShop.SQL;

import com.artShop.Interfases.CRUD;
import com.artShop.Service.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryCRUD implements CRUD<Delivery, ResultSet> {
    static {
        SQLDataBase.register("Delivery", new DeliveryCRUD());
    }

    public final String COLLECTION_NAME = "delivery";
    private final SQLDataBase instance;

    private DeliveryCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    @Override
    public void insertOne(Delivery delivery) throws Exception {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`id_order`, `client`, `telephone`, `email`, `address`, `datetime`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
     //   stmt.setInt(1, delivery.getIdOrder());
        stmt.setString(2, delivery.getClient());
        stmt.setString(3, delivery.getTelephone());
        stmt.setString(4, delivery.getEmail());
        stmt.setString(5, delivery.getAddress());
        stmt.setString(6, delivery.getDateTime());
        stmt.executeUpdate();
    }

    @Override
    public void insertMany(List<Delivery> deliveries) throws Exception {
        String params = deliveries.stream().map((el) -> "(?, ?, ?, ?, ?, ?)").collect(Collectors.joining(", "));

        String query = "INSERT INTO " + COLLECTION_NAME + " (`id_order`, `client`, `telephone`, `email`, `address`, `datetime`) " +
                "VALUES " + params;
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        int size = deliveries.size();
        for (int i = 0; i < size; i++) {
            Delivery delivery = deliveries.get(i);
          //  stmt.setInt(i * 6 + 1, delivery.getIdOrder());
            stmt.setString(i * 6 + 2, delivery.getClient());
            stmt.setString(i * 6 + 3, delivery.getTelephone());
            stmt.setString(i * 6 + 4, delivery.getEmail());
            stmt.setString(i * 6 + 5, delivery.getAddress());
            stmt.setString(i * 6 + 6, delivery.getDateTime());
        }
        stmt.executeUpdate();
    }

    @Override
    public List<Delivery> findAll() throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + COLLECTION_NAME);
        ResultSet res = stmt.executeQuery();
        return toList(res);
    }

    @Override
    public void updateOne(Delivery newDelivery, Object id) throws Exception {
        String query = "UPDATE " + COLLECTION_NAME + " SET `id_order`= ?,`client`= ?,`telephone`= ?,`email`= ?,`address`= ?,`datetime`= ? WHERE id = ?";

        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
     //   stmt.setInt(1, newDelivery.getOrder());
        stmt.setString(2, newDelivery.getClient());
        stmt.setString(3, newDelivery.getTelephone());
        stmt.setString(4, newDelivery.getEmail());
        stmt.setString(5, newDelivery.getAddress());
        stmt.setString(6, newDelivery.getDateTime());
        stmt.setInt(7, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public void deleteOne(Object id) throws Exception {
        String query = "DELETE FROM " + COLLECTION_NAME + " WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setInt(1, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public List<Delivery> toList(ResultSet items) throws SQLException {
        ArrayList<Delivery> delivery = new ArrayList<>();
        while (items.next()) {
//            delivery.add(new Delivery(
//                    items.getInt(1),
//                    items.getString(2),
//                    items.getString(3),
//                    items.getString(4),
//                    items.getString(5),
//                    items.getString(6)
//            ));
        }
        return delivery;
    }
}
