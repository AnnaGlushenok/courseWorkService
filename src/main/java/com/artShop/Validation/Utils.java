package com.artShop.Validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class Utils {

    public static String sendError(BindingResult res) {
        List<ObjectError> allErrors = res.getAllErrors();
        StringBuilder errors = new StringBuilder();
        for (int i = 0; i < res.getErrorCount(); i++) {
            var error = (FieldError) allErrors.get(i);
            errors.append(error.getField()).append(" ").append(allErrors.get(i).getDefaultMessage()).append("\n");
        }
        return errors.toString();
    }
}
