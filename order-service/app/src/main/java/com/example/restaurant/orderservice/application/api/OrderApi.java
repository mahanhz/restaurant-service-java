package com.example.restaurant.orderservice.application.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Set;

public record OrderApi(Long id, String state, @JsonFormat(pattern="yyyy-MM-dd") LocalDate date, Set<ItemApi> items) {
    public OrderApi(LocalDate date, Set<ItemApi> items) {
        this(null, null, date, items);
    }
}
