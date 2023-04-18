package com.example.restaurant.orderservice.domain;

import java.time.LocalDate;
import java.util.Set;

public record Order(OrderId orderId, LocalDate date, State state, Set<Item> items) {
}
