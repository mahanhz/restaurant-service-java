package com.example.restaurant.kitchenservice.application.exception;

public class KitchenServiceException extends RuntimeException {

    private final ErrorCode code;

    public KitchenServiceException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public KitchenServiceException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public KitchenServiceException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }

    public KitchenServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
