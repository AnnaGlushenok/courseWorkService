package com.artShop.SQL;

import com.artShop.Interfases.CRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryCRUD implements CRUD<Category, ResultSet> {

    static {
        SQLDataBase.register("Category", new CategoryCRUD());
    }

    public final String COLLECTION_NAME = "categories";
    private final SQLDataBase instance;

    private CategoryCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    @Override
    public void insertOne(Category category) throws Exception {
        String query = "INSERT INTO " + COLLECTION_NAME + " (`name`) VALUES (?)";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setString(1, category.getName());
        stmt.executeUpdate();
    }

    @Override
    public void insertMany(List<Category> categories) throws Exception {
        String params = categories.stream().map((el) -> "(?)").collect(Collectors.joining(", "));
        String query = "INSERT INTO " + COLLECTION_NAME + " (`name`) VALUES " + params;
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);

        int size = categories.size();
        for (int i = 1; i <= size; i++) {
            Category r = categories.get(i - 1);
            stmt.setString(i, r.getName());
        }
        stmt.executeUpdate();
    }

    @Override
    public List<Category> findAll() throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM " + COLLECTION_NAME);
        ResultSet res = stmt.executeQuery();
        return toList(res);
    }

    @Override
    public void updateOne(Category newCategory, Object id) throws Exception {
        String query = "UPDATE " + COLLECTION_NAME + " SET `name`= ? WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setString(1, newCategory.getName());
        stmt.setInt(2, (int) id);
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
    public List<Category> toList(ResultSet items) throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        while (items.next()) {
            categories.add(new Category(
                    items.getString(2)
            ));
        }
        return categories;
    }
}
