package com.artShop.Interfases;

import com.artShop.DataBases.Entity;

public interface DataBase {
    CRUD getEntity(Entity key);
}
