package com.artShop.Exceptions;

public class NoSuchIdException extends NumberFormatException {
    public NoSuchIdException() {
        super("Неверный idProduct");
    }
}
