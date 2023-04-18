package com.example.restaurant.orderservice.application.command;

import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import com.example.restaurant.orderservice.domain.Item;
import com.example.restaurant.orderservice.domain.Name;
import com.example.restaurant.orderservice.domain.Order;
import com.example.restaurant.orderservice.domain.OrderId;
import com.example.restaurant.orderservice.domain.event.OrderCreatedEvent;
import com.example.restaurant.orderservice.domain.service.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommandHandler.class);

    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;

    public OrderCommandHandler(final OrderService orderService, final ApplicationEventPublisher eventPublisher) {
        this.orderService = orderService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public OrderApi createOrder(final OrderApi order) {
        final Set<Item> items = order.items().stream().map(item -> new Item(new Name(item.name()), item.quantity())).collect(Collectors.toSet());
        final Order createdOrder = orderService.createOrder(order.date(), items);

        eventPublisher.publishEvent(OrderCreatedEvent.of(createdOrder));

        return new OrderApi(
                createdOrder.orderId().value(), createdOrder.state().toString(), createdOrder.date(), order.items()
        );
    }

    public OrderApi order(final Long id) {
        final Order order = orderService.order(new OrderId(id));

        return new OrderApi(
                order.orderId().value(),
                order.state().toString(),
                order.date(),
                order.items().stream().map(item -> new ItemApi(item.name().value(), item.quantity())).collect(Collectors.toSet())
        );
    }
}