package com.example.restaurant.orderservice.domain.event;

import com.example.restaurant.common.event.ExportedEvent;
import com.example.restaurant.orderservice.domain.Item;
import com.example.restaurant.orderservice.domain.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OrderCreatedEvent implements ExportedEvent {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final long id;
    private final JsonNode payload;

    private OrderCreatedEvent(long id, JsonNode payload) {
        this.id = id;
        this.payload = payload;
    }

    public static OrderCreatedEvent of(final Order order) {
        final ObjectNode asJson = MAPPER.createObjectNode()
                .put("id", order.orderId().value())
                .put("date", order.date().toString())
                .put("state", order.state().toString());

        var arrayNode = asJson.putArray("items");
        for (Item item: order.items()) {
            arrayNode.add(
                    MAPPER.createObjectNode()
                            .put("name", item.name().value())
                            .put("quantity", item.quantity())
            );
        }

        return new OrderCreatedEvent(order.orderId().value(), asJson);
    }

    @Override
    public String aggregateId() {
        return String.valueOf(id);
    }

    @Override
    public String aggregateType() {
        return "Order";
    }

    @Override
    public JsonNode payload() {
        return payload;
    }

    @Override
    public String type() {
        return "OrderCreated";
    }
}
