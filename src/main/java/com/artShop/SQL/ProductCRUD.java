package com.artShop.SQL;

import com.artShop.Interfases.CRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCRUD implements CRUD<Product, ResultSet> {

    static {
        SQLDataBase.register("Product", new ProductCRUD());
    }

    public final String COLLECTION_NAME = "product";
    private final SQLDataBase instance;

    private ProductCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    @Override
    public void insertOne(Product prod) throws Exception {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setString(1, prod.getProductCode());
        stmt.setString(2, prod.getName());
        stmt.setString(3, prod.getDescription());
        stmt.setInt(4, prod.getPrice());
        stmt.setInt(5, prod.getAmount());

        stmt.executeUpdate();
    }

    @Override
    public void insertMany(List<Product> products) throws Exception {
        String params = products.stream().map((el) -> "(?, ?, ?, ?, ?)").collect(Collectors.joining(", "));

        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES " + params;
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);

        int size = products.size();
        for (int i = 0; i < size; i++) {
            Product prod = products.get(i);
            stmt.setString(i * 5 + 1, prod.getProductCode());
            stmt.setString(i * 5 + 2, prod.getName());
            stmt.setString(i * 5 + 3, prod.getDescription());
            stmt.setInt(i * 5 + 4, prod.getPrice());
            stmt.setInt(i * 5 + 5, prod.getAmount());
        }
        stmt.executeUpdate();
    }

    @Override
    public List<Product> findAll() throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + COLLECTION_NAME);
        ResultSet res = stmt.executeQuery();
        return toList(res);
    }

    @Override
    public void updateOne(Product newProduct, Object id) throws Exception {
        String query = "UPDATE " + COLLECTION_NAME + " SET `product_code`= ?,`name`= ?,`description`= ?,`price`= ?,`amount`= ? WHERE id = ?";

        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setString(1, newProduct.getProductCode());
        stmt.setString(2, newProduct.getName());
        stmt.setString(3, newProduct.getDescription());
        stmt.setDouble(4, newProduct.getPrice());
        stmt.setInt(5, newProduct.getAmount());
        stmt.setInt(6, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public void deleteOne(Object id) throws SQLException {
        String query = "DELETE FROM " + COLLECTION_NAME + " WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setInt(1, (int) id);
        stmt.executeUpdate();
    }

    @Override
    public List<Product> toList(ResultSet items) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        while (items.next()) {
            products.add(new Product(
                    items.getString(2),
                    items.getString(3),
                    items.getString(4),
                    items.getInt(5),
                    items.getInt(6)
            ));
        }
        return products;
    }
}
