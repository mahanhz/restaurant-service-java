package com.example.restaurant.orderservice.application.exception;

public enum ErrorCode {
    ERR_ORD_SER_1("Order not found");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }
}
