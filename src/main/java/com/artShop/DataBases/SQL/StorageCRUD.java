package com.artShop.DataBases.SQL;

import com.artShop.DataBases.Entity;
import com.artShop.Interfases.CRUD;
import com.artShop.Service.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StorageCRUD implements CRUD<Storage, ResultSet> {
    static {
        SQLDataBase.register(Entity.Storage, new StorageCRUD());
    }

    public final String COLLECTION_NAME = "storage_products";
    private final SQLDataBase instance;

    private StorageCRUD() {
        this.instance = SQLDataBase.getInstance();
    }

    @Override
    public void insertOne(Storage storage) throws Exception {
        List<Storage.Products> products = storage.getProducts();
        int size = storage.getProducts().size();
        String params = products.stream().map((el) -> "(?, ?, ?)").collect(Collectors.joining(", "));
        String query = "INSERT INTO " + COLLECTION_NAME + " (`id_storage`, `id_product`, `amount`) VALUES " + params;
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);

        int id = (int) storage.getIdStorage();
        for (int i = 0; i < size; i++) {
            stmt.setInt(i * 3 + 1, id);
            stmt.setInt(i * 3 + 2, (int) storage.getProducts().get(i).getProductId());
            stmt.setInt(i * 3 + 3, storage.getProducts().get(i).getAmount());
        }
        stmt.executeUpdate();
    }

    @Override
    public List<Storage> findAll(int limit, int offset) throws Exception {
        PreparedStatement stmt = instance.getConnection().prepareStatement("SELECT * FROM storage_view limit ? offset ?");
        stmt.setInt(1, limit);
        stmt.setInt(2, offset);
        return toList(stmt.executeQuery());
    }

    @Override
    public void updateOne(Storage storage, Object id) throws Exception {
        String query = "UPDATE " + COLLECTION_NAME + " SET `id_storage`= ?,`id_product`= ?,`amount`= ? WHERE id = ?";
        PreparedStatement stmt = instance.getConnection().prepareStatement(query);
        stmt.setInt(1, (int) storage.getIdStorage());
        stmt.setInt(2, (int) storage.getProducts().get(0).getProductId());
        stmt.setInt(3, storage.getProducts().get(0).getAmount());
        stmt.setInt(4, (int) id);
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
    public List<Storage> toList(ResultSet items) throws Exception {
        ArrayList<Storage> storages = new ArrayList<>();
        while (items.next()) {
            String[] productsIds = items.getString(2).split(";");
            String[] amountsIds = items.getString(3).split(";");
            ArrayList<Storage.Products> products = new ArrayList<>();
            for (int i = 0; i < productsIds.length; i++)
                products.add(new Storage.Products(Integer.parseInt(productsIds[i]), Integer.parseInt(amountsIds[i])));

            storages.add(new Storage(
                    items.getString(1),
                    products
            ));
        }
        return storages;
    }
}
