package com.example.restaurant.kitchenservice.domain;

import java.util.Set;

public record Chef(Id id, Name name, Set<Id> orders) {
    public boolean isAvailable() {
        return orders.size() < 3;
    }
}

