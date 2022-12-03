package com.artShop.Exceptions;

import javax.servlet.http.HttpServletResponse;

public class CustomException extends Exception {
    private String message;
    private int status;
    private Exception exception;

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Exception getException() {
        return exception;
    }

    public CustomException(String message, int status, Exception exception) {
        super(message);
        this.status = status;
        this.exception = exception;
    }

    public CustomException(String message, int status) {
        super(message);
        this.status = status;
    }
}
