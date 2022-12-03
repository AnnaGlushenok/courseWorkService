package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Exceptions.CustomException;
import com.artShop.Interfases.IProduct;
import com.artShop.Service.Product;

import javax.servlet.http.HttpServletResponse;
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

    private void productCategoryInsert(List<String> codes, List<String> categories, Connection connection) throws CustomException {
        String findIdProductQuery = "SELECT id FROM " + COLLECTION_NAME + " WHERE `product_code`= ?";
        String findIdCategoryQuery = "SELECT id FROM categories WHERE `name` = ?";

        String params = codes.stream().map((el) -> "(?, ?)").collect(Collectors.joining(", "));
        String insertQuery = "INSERT INTO `product_category` (`id_category`, `id_product`) VALUES " + params;

        try {
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
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void productCategoryDelete(int id) throws CustomException {
        String deleteQuery = "DELETE FROM `product_category` WHERE `id_product` = ?";
        try {
            Connection connection = instance.getConnection();
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void insertOne(Product prod) throws CustomException {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
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
            } catch (CustomException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void insertMany(List<Product> products) throws CustomException {
        String params = products.stream().map((el) -> "(?, ?, ?, ?, ?)").collect(Collectors.joining(", "));
        String query = "INSERT INTO " + COLLECTION_NAME + " (`product_code`, `name`, `description`, `price`, `amount`) " +
                "VALUES " + params;

        try {
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
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Product> findAll(int limit, int offset) throws CustomException {
        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM product_view limit ? offset ?");
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            return toList(stmt.executeQuery());
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateOne(Product newProduct, Object id) throws CustomException {
        String query = "UPDATE " + COLLECTION_NAME + " SET `product_code`= ?,`name`= ?,`description`= ?,`price`= ?,`amount`= ? WHERE id = ?";

        try {
            PreparedStatement stmt = instance.getConnection().prepareStatement(query);
            stmt.setString(1, newProduct.getProductCode());
            stmt.setString(2, newProduct.getName());
            stmt.setString(3, newProduct.getDescription());
            stmt.setDouble(4, newProduct.getPrice());
            stmt.setInt(5, newProduct.getAmount());
            stmt.setInt(6, (int) id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteOne(Object id) throws CustomException {
        String query = "DELETE FROM " + COLLECTION_NAME + " WHERE id = ?";

        try {
            Connection connection = instance.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, (int) id);
            productCategoryDelete((int) id);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public List<Product> toList(ResultSet items) throws CustomException {
        ArrayList<Product> products = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            throw new CustomException("Ошибка с базой данных", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return products;
    }
}
