//package com.artShop.SQL;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CategoryCRUD {
//    public final String COLLECTION_NAME = "categories";
//    private final SQLDataBase dataBase;
//    private final Connection connection;
//
//    public CategoryCRUD() {
//        this.dataBase = SQLDataBase.getInstance();
//        this.connection = dataBase.getConnection();
//    }
//
//    public ResultSet findAll() throws SQLException {
//        return dataBase.findAll(COLLECTION_NAME);
//    }
//
//    public ResultSet findByCriteria(String criteria, String value) throws SQLException {
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ? WHERE ? = ?");
//        stmt.setString(2, criteria);
//        stmt.setString(3, value);
//        return dataBase.findByCriteria(COLLECTION_NAME, stmt);
//    }
//
//    public <T extends List<String>> void insert(T values) throws SQLException {
//        PreparedStatement stmt = connection.prepareStatement(
//                "INSERT INTO ? (`product_code`, `name`, `description`, `price`, `amount`) " +
//                "VALUES (?, ?, ?, ?, ?)");
//        stmt.setString(2, values.get(1));
//        stmt.setString(3, values.get(2));
//        stmt.setString(4, values.get(3));
//        stmt.setString(5, values.get(4));
//        dataBase.insert(COLLECTION_NAME, stmt);
//    }
//
//    public <T extends Product> void insertMany(List<T> products) throws SQLException {
//        StringBuilder sql = new StringBuilder();
//        int size = products.size();
//        for (int i = 0; i < size; i++) {
//            sql.append("(").append("?, ?, ?, ?, ?").append(")");
//            if (i != size - 1)
//                sql.append(", ");
//        }
//        PreparedStatement stmt = connection.prepareStatement("INSERT INTO ? (`product_code`, `name`, `description`, `price`, `amount`) " +
//                "VALUES " + sql);
//
//        for (int i = 0; i < size; i++) {
//            Product prod = products.get(i);
//            stmt.setString(i * 5 + 2, prod.getProductCode());
//            stmt.setString(i * 5 + 2, prod.getName());
//            stmt.setString(i * 5 + 2, prod.getDescription());
//            stmt.setInt(i * 5 + 2, prod.getPrice());
//            stmt.setInt(i * 5 + 2, prod.getAmount());
//
//        }
//        dataBase.insertMany(COLLECTION_NAME, stmt);
//    }
//
//    public void update(String[] cond, ArrayList<String> desiredFields, ArrayList<String> desiredValues) throws SQLException {
//        StringBuilder sql = new StringBuilder("UPDATE ? SET ");
//        int size = desiredFields.size();
//        for (int i = 0; i < size; i++) {
//            sql.append("? = ?");
//            if (i != size - 1)
//                sql.append(", ");
//        }
//        sql.append("WHERE ? = ?");
//
//        PreparedStatement stmt = connection.prepareStatement(sql.toString());
//        stmt.setString(1, COLLECTION_NAME);
//        for (int i = 0; i < size; i++) {
//            stmt.setString(i, desiredFields.get(i));
//            stmt.setString(i, desiredValues.get(i));
//        }
//        dataBase.update(cond[0], cond[1], stmt);
//    }
//
//    public void deleteOne(String collectionName, String field, String value) throws SQLException {
//        dataBase.deleteOne(COLLECTION_NAME, field, value);
//    }
//}
