package com.example.restaurant.kitchenservice.application.api;

import java.util.Optional;
import java.util.Set;

public record ChefApi(Long id, String chef, Set<Long> orders, Set<String> errors) {
    public ChefApi(String chef, Set<Long> orders, Set<String> errors) {
        this(null, chef, orders, errors);
    }
}
