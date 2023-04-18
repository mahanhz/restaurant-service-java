package com.example.restaurant.orderservice.infra.web;

import com.example.restaurant.orderservice.application.exception.OrderServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice(basePackages = {"com.example.restaurant.orderservice"})
public class OrderControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, Object>> catchAll(OrderServiceException ex, WebRequest request) {
        LOGGER.error("Caught exception", ex);

        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", String.format("%s - %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
        body.put("code", ex.getCode());
        body.put("message", ex.getCode().getMessage());
        // body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
