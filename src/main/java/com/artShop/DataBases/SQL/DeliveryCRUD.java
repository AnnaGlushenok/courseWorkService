package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Delivery;
import com.artShop.Service.Order;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryCRUD implements CRUD<Delivery, ResultSet> {
    static {
        SQLDataBase.register(Entity.Delivery, new DeliveryCRUD());
    }

    public final String COLLECTION_NAME = "delivery";
    private final SQLDataBase instance;

    private DeliveryCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    private void insertOrders(ArrayList<Order> orders, int idOrder) throws CustomException {
        try {
            String params = orders.stream().map((el) -> "(?, ?, ?)").collect(Collectors.joining(", "));
            String query = "INSERT INTO `orders` (`id`, `id_product`, `amount`) VALUES " + params;
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);

            int size = orders.size();
            for (int i = 0; i < size; i++) {
                stmt.setInt(i * 2 + 1, idOrder + 1);
                stmt.setInt(i * 2 + 2, (Integer) orders.get(i).getProductId());
                stmt.setInt(i * 2 + 3, orders.get(i).getAmount());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void updateOrder(Order order, int id) throws CustomException {
        try {
            String query = "UPDATE `orders` SET `id_product`= ?,`amount`= ? WHERE id = ?";
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);
            stmt.setInt(1, (int) order.getProductId());
            stmt.setInt(2, order.getAmount());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void deleteOrder(int id) throws CustomException {
        try {
            String deleteQuery = "DELETE FROM `orders` WHERE id = ?";
            PreparedStatement deleteQueryStmt = instance.getConnection().prepareStatement(deleteQuery);
            deleteQueryStmt.setInt(1, id);
            deleteQueryStmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void insertOne(Delivery delivery) throws CustomException {
        try {
            String query = "INSERT INTO " + COLLECTION_NAME + " (`id_order`, `client`, `telephone`, `email`, `address`, `datetime`, `confirmed`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            Connection connection = instance.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement findMaxStmt = connection.prepareStatement("Select max(id) from orders");
            ResultSet res = findMaxStmt.executeQuery();
            res.next();
            int maxId = res.getInt(1);
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                insertOrders(delivery.getListOrders(), maxId);
                stmt.setInt(1, maxId + 1);
                stmt.setString(2, delivery.getClient());
                stmt.setString(3, delivery.getTelephone());
                stmt.setString(4, delivery.getEmail());
                stmt.setString(5, delivery.getAddress());
                stmt.setString(6, delivery.getDatetime());
                stmt.setBoolean(7, delivery.getConfirmed());
                stmt.executeUpdate();
                connection.commit();
            } catch (CustomException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Delivery> findAll(int limit, int offset) throws CustomException {
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM delivery_view limit ? offset ?");
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet res = stmt.executeQuery();
            return toList(res);
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateOne(Delivery newDelivery, Object id) throws CustomException {
        try {
            String updateQuery = "UPDATE " + COLLECTION_NAME + " SET `client`= ?,`telephone`= ?,`email`= ?,`address`= ?,`datetime`= ?, `confirmed` = ? WHERE id = ?";

            Connection connection = instance.getConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement updateQueryStmt = connection.prepareStatement(updateQuery);
                String findIdQuery = "SELECT id_order FROM `delivery` WHERE id = ?";
                PreparedStatement findIdQueryStmt = connection.prepareStatement(findIdQuery);
                findIdQueryStmt.setInt(1, (int) id);
                ResultSet res = findIdQueryStmt.executeQuery();
                res.next();
                int idOrder = res.getInt(1);

                updateOrder(newDelivery.getOrders()[0], idOrder);

                updateQueryStmt.setString(1, newDelivery.getClient());
                updateQueryStmt.setString(2, newDelivery.getTelephone());
                updateQueryStmt.setString(3, newDelivery.getEmail());
                updateQueryStmt.setString(4, newDelivery.getAddress());
                updateQueryStmt.setString(5, newDelivery.getDatetime());
                updateQueryStmt.setBoolean(6, newDelivery.getConfirmed());
                updateQueryStmt.setInt(7, (int) id);
                updateQueryStmt.executeUpdate();
                connection.commit();
            } catch (CustomException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteOne(Object id) throws CustomException {
        String query = "DELETE FROM " + COLLECTION_NAME + " WHERE id = ?";
        String findIdQuery = "SELECT `id_order` FROM `delivery` WHERE id = ?";
        try {
            Connection connection = instance.getConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                PreparedStatement findIdQueryStmt = instance.getConnection().prepareStatement(findIdQuery);
                findIdQueryStmt.setInt(1, (int) id);
                ResultSet res = findIdQueryStmt.executeQuery();
                res.next();
                int idOrder = res.getInt(1);
                stmt.setInt(1, (int) id);
                stmt.executeUpdate();
                deleteOrder(idOrder);
                connection.commit();
            } catch (CustomException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public List<Delivery> toList(ResultSet items) throws CustomException {
        ArrayList<Delivery> delivery = new ArrayList<>();
        try {
            while (items.next()) {
                delivery.add(new Delivery(
                        new Order[]{
                                new Order(
                                        items.getInt(8),
                                        items.getObject(7)
                                )},
                        items.getString(1),
                        items.getString(2),
                        items.getString(3),
                        items.getString(4),
                        items.getString(5),
                        items.getBoolean(6)
                ));
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return delivery;
    }
}
