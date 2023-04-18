package com.example.restaurant.orderservice.integrationtest;

import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import com.example.restaurant.orderservice.application.command.OrderCommandHandler;
import com.example.restaurant.orderservice.domain.event.OrderCreatedEvent;
import com.example.restaurant.orderservice.domain.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RecordApplicationEvents
class CommandHandlerIT extends DatabaseTest {
    @Autowired
    OrderService orderService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    ApplicationEvents events;

    @Test
    public void should_publish_event_when_order_is_created() {
        final OrderCommandHandler commandHandler = new OrderCommandHandler(orderService, eventPublisher);
        final OrderApi order = commandHandler.createOrder(
                new OrderApi(
                        LocalDate.of(2023, 3, 5),
                        Set.of(new ItemApi("Burger", 1))
                )
        );

        final List<OrderCreatedEvent> orderCreatedEvents = events.stream(OrderCreatedEvent.class).toList();

        assertThat(orderCreatedEvents).hasSize(1).satisfies(it -> {
            assertThat(it.get(0).aggregateId()).isEqualTo(order.id().toString());
            assertThat(it.get(0).aggregateType()).isEqualTo("Order");
            assertThat(it.get(0).type()).isEqualTo("OrderCreated");
        });
    }
}