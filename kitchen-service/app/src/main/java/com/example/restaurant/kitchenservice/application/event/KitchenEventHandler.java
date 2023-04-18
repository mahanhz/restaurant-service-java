package com.example.restaurant.kitchenservice.application.event;

import com.example.restaurant.common.event.ExportedEvent;
import com.example.restaurant.kitchenservice.application.command.KitchenCommandHandler;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class KitchenEventHandler {
    private final KitchenCommandHandler kitchenCommandHandler;

    public KitchenEventHandler(KitchenCommandHandler kitchenCommandHandler) {
        this.kitchenCommandHandler = kitchenCommandHandler;
    }

    @Async
    @EventListener
    public void handle(final ExportedEvent event) {
        if (event.type().equals("OrderCreated")) {
            var orderId = event.aggregateId();

            kitchenCommandHandler.assignChef(Long.parseLong(orderId));
        }
    }
}
