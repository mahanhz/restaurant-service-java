package com.example.restaurant.orderservice.domain.service;


import com.example.restaurant.orderservice.domain.Item;
import com.example.restaurant.orderservice.domain.Order;
import com.example.restaurant.orderservice.domain.OrderId;

import java.time.LocalDate;
import java.util.Set;

public interface OrderService {

    Order createOrder(LocalDate date, Set<Item> items);
    Order order(OrderId id);
}
