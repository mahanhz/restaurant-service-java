package com.example.restaurant.orderservice.domain;

import static org.apache.commons.lang3.Validate.isTrue;

public record Item(Name name, int quantity) {

    public Item {
        isTrue(quantity > 0, "The must be at least one of item %d", name.value());
    }
}
