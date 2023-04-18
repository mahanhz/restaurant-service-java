package com.example.restaurant.orderservice.application.exception;

public class OrderServiceException extends RuntimeException {

    private final ErrorCode code;

    public OrderServiceException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public OrderServiceException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public OrderServiceException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }

    public OrderServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
