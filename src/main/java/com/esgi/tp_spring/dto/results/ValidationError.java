package com.esgi.tp_spring.dto.results;

import java.util.HashMap;
import java.util.Map;

public class ValidationError {

    private String errorMessage;

    private Map<String, String> fieldErrors = new HashMap<>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String field, String error) {
        this.fieldErrors.put(field, error);
    }
}
