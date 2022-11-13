package com.artShop.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCRUD extends SQLDataBase {
    public final String COLLECTION_NAME = "product";

    public ProductCRUD(String url, String dataBase) throws SQLException {
        super(url + "/" + dataBase);
    }

    public ResultSet findAll() throws SQLException {
        return super.findAll("SELECT * FROM product");
    }
}
