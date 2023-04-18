package com.example.restaurant.kitchenservice.application.api;

import java.util.Set;

public record DetailedChefApi(long id, String chef, Set<OrderDetailsApi> orders) {
}
