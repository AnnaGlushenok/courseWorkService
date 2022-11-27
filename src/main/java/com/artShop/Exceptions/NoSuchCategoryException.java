package com.artShop.Exceptions;

import java.sql.SQLException;

public class NoSuchCategoryException extends SQLException {
    public NoSuchCategoryException(Throwable err) {
        super("Отстутствует введённая категория", err);
    }
}
