package com.example.restaurant.orderservice.infra.persistence;

import com.example.restaurant.orderservice.application.exception.ErrorCode;
import com.example.restaurant.orderservice.application.exception.OrderServiceException;
import com.example.restaurant.orderservice.domain.*;
import com.example.restaurant.orderservice.domain.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public JpaOrderService(final OrderRepository orderRepository, final ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public Order createOrder(final LocalDate date, final Set<Item> items) {
        final Set<ItemEntity> itemEntities = items.stream().map(item -> new ItemEntity(item.name().value(), item.quantity())).collect(Collectors.toSet());
        itemRepository.saveAll(itemEntities);

        final OrderEntity orderEntity = new OrderEntity(date, State.CREATED, itemEntities);
        final OrderEntity order = orderRepository.save(orderEntity);

        return new Order(
                new OrderId(order.getId()),
                order.getDate(),
                order.getState(),
                order.getItems().stream().map(item -> new Item(new Name(item.getName()), item.getQuantity())).collect(Collectors.toSet())
        );
    }

    @Override
    public Order order(final OrderId id) {
        final OrderEntity order =
                orderRepository
                        .findById(id.value())
                        .orElseThrow(() -> new OrderServiceException(
                                String.format("Could not find order with id %d", id.value()),
                                ErrorCode.ERR_ORD_SER_1)
                        );

        return new Order(
                new OrderId(order.getId()),
                order.getDate(),
                order.getState(),
                order.getItems().stream().map(item -> new Item(new Name(item.getName()), item.getQuantity())).collect(Collectors.toSet())
        );
    }
}
