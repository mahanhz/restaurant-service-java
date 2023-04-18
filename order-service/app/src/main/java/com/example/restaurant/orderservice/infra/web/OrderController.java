package com.example.restaurant.orderservice.infra.web;

import com.example.restaurant.orderservice.application.api.OrderApi;
import com.example.restaurant.orderservice.application.command.OrderCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderCommandHandler orderCommandHandler;

    public OrderController(final OrderCommandHandler orderCommandHandler) {
        this.orderCommandHandler = orderCommandHandler;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderApi> createOrder(@RequestBody OrderApi order) {
        final OrderApi createdOrder = orderCommandHandler.createOrder(order);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.id()).toUri();
        LOGGER.info("Created order {} at uri {}", createdOrder, uri);
        return ResponseEntity.created(uri).body(createdOrder);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderApi> order(@PathVariable Long id) {
        final OrderApi order = orderCommandHandler.order(id);

        return ResponseEntity.ok(order);
    }
}
