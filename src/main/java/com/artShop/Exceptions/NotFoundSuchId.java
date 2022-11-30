package com.artShop.Exceptions;

import java.sql.SQLException;

public class NotFoundSuchId extends SQLException {
    public NotFoundSuchId() {
        super("Не найден id");
    }
}
