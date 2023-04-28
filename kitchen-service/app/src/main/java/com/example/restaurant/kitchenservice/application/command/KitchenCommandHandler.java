package com.example.restaurant.kitchenservice.application.command;

import com.example.restaurant.kitchenservice.application.api.ChefApi;
import com.example.restaurant.kitchenservice.application.api.DetailedChefApi;
import com.example.restaurant.kitchenservice.application.api.OrderDetailsApi;
import com.example.restaurant.kitchenservice.application.exception.KitchenServiceException;
import com.example.restaurant.kitchenservice.domain.Chef;
import com.example.restaurant.kitchenservice.domain.Id;
import com.example.restaurant.kitchenservice.domain.Name;
import com.example.restaurant.kitchenservice.domain.service.KitchenService;
import com.example.restaurant.orderservice.application.command.OrderCommandHandler;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KitchenCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(KitchenCommandHandler.class);

    private final KitchenService kitchenService;
    private final OrderCommandHandler orderCommandHandler;

    public KitchenCommandHandler(final KitchenService kitchenService, final OrderCommandHandler orderCommandHandler) {
        this.kitchenService = kitchenService;
        this.orderCommandHandler = orderCommandHandler;
    }

    // Order order = null;

    @Transactional
    public ChefApi createChef(String chefName) {
        var chef = kitchenService.createChef(new Name(chefName));

        return new ChefApi(
                chef.id().value(),
                chef.name().value(),
                chef.orders().stream().map(Id::value).collect(Collectors.toSet()),
                Collections.emptySet()
        );
    }

    public DetailedChefApi chef(long id) {
        final Chef chef = kitchenService.chef(new Id(id));

        final Set<OrderDetailsApi> orderDetailsApi =
                chef.orders().stream()
                        .flatMap(
                                order -> orderCommandHandler.order(order.value()).items().stream().map(
                                        item -> new OrderDetailsApi(order.value(), item.name(), item.quantity())
                                )
                        ).collect(Collectors.toSet());

        return new DetailedChefApi(
                chef.id().value(),
                chef.name().value(),
                orderDetailsApi
        );
    }

    @Transactional
    public ChefApi assignChef(long orderId) {
        try {
            final Chef chef = kitchenService.assignChef(orderId);
            return new ChefApi(
                    chef.id().value(),
                    chef.name().value(),
                    chef.orders().stream().map(Id::value).collect(Collectors.toSet()),
                    Collections.emptySet()
            );
        } catch (final KitchenServiceException kse) {
            return new ChefApi(
                    null,
                    Set.of(orderId),
                    Set.of(String.format("%s - %s", kse.getCode(), kse.getMessage()))
            );
        }
    }
}