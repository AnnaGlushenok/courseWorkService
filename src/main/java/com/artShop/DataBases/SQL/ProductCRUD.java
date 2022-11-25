package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCRUD implements IProduct<Product, ResultSet> {

    static {
        SQLDataBase.register(Entity.Product, new ProductCRUD());
    }

    public final String COLLECTION_NAME = "product";
    private final SQLDataBase instance;

    private ProductCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    private void productCategoryInsert(List<String> codes, List<String> categories, Connection connection) throws SQLException {
        String findIdProductQuery = "SELECT id FROM " + COLLECTION_NAME + " WHERE `product_code`= ?";
        String findIdCategoryQuery = "SELECT id FROM " + CategoryCRUD.COLLECTION_NAME + " WHERE `name` = ?";

        String params = codes.stream().map((el) -> "(?, ?)").collect(Collectors.joining(", "));
        String insertQuery = "INSERT INTO `product_category` (`id_category`, `id_product`) VALUES " + params;

        PreparedStatement idProductStmt = connection.prepareStatement(findIdProductQuery);
        PreparedStatement idCategoryStmt = connection.prepareStatement(findIdCategoryQuery);
        PreparedStatement insertStmt = connection.prepareStatement(insertQuery);

        int size = codes.size();
        for (int i = 0; i < size; i++) {
            idProductStmt.setString(1, codes.get(i));
            idCategoryStmt.setString(1, categories.get(i));

            ResultSet idProductRes = idProductStmt.executeQuery();
            ResultSet idCategoryRes = idCategoryStmt.executeQuery();
            idProductRes.next();
            idCategoryRes.next();
            int idProduct = idProductRes.getInt("id");
            int idCategory = idCategoryRes.getInt("id");
            insertStmt.setInt(i * 2 + 1, idCategory);
            insertStmt.setInt(i * 2 + 2, idProduct);
        }

        insertStmt.executeUpdate();
    }

    private void productCategoryDelete(int id) throws SQLException {
        String deleteQuery = "DELETE FROM `product_category` WHERE `id_product` = ?";
        Connection connection = instance.getConnection();
        PreparedStatement stmt = connection.prepareStatement(deleteQuery);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public void insertOne(Product prod) throws Exception {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection connection = instance.getConnection();
        connection.setAutoCommit(false);
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, prod.getProductCode());
            stmt.setString(2, prod.getName());
            stmt.setString(3, prod.getDescription());
            stmt.setInt(4, prod.getPrice());
            stmt.setInt(5, prod.getAmount());

            stmt.executeUpdate();
            productCategoryInsert(
                    new ArrayList<String>() {{
                        add(prod.getProductCode());
                    }},
                    new ArrayList<String>() {{
                        add(prod.getCategory());
                    }},
                    connection
            );

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }
    }

    @Override
    public void insertMany(List<Product> products) throws Exception {
        String params = products.stream().map((el) -> "(?, ?, ?, ?, ?)").collect(Collectors.joining(", "));
        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES " + params;

        Connection connection = instance.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement stmt = connection.prepareStatement(query);

        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        int size = products.size();
        for (int i = 0; i < size; i++) {
            Product prod = products.get(i);
            String code = prod.getProductCode();
            codes.add(code);
            categories.add(prod.getCategory());
            stmt.setString(i * 5 + 1, code);
            stmt.setString(i * 5 + 2, prod.getName());
            stmt.setString(i * 5 + 3, prod.getDescription());
            stmt.setInt(i * 5 + 4, prod.getPrice());
            stmt.setInt(i * 5 + 5, prod.getAmount());
        }
        stmt.executeUpdate();
        productCategoryInsert(codes, categories, connection);
        connection.commit();
    }

    @Override
    public List<Product> findAll(int limit, int offset) throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM product_view limit ? offset ?");
        stmt.setInt(1, limit);
        stmt.setInt(2, offset);
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

        Connection connection = instance.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, (int) id);
        productCategoryDelete((int) id);
        stmt.executeUpdate();
        connection.commit();
    }

    @Override
    public List<Product> toList(ResultSet items) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        while (items.next()) {
            products.add(new Product(
                    items.getString(7),
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
