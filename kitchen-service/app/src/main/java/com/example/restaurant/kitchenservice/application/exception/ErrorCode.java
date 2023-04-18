package com.example.restaurant.kitchenservice.application.exception;

public enum ErrorCode {
    ERR_KIT_SER_1("Chef not found"),
    ERR_KIT_SER_2("Chef not available");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }
}
