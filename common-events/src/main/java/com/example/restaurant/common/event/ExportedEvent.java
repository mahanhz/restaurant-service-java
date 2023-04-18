package com.example.restaurant.common.event;

import com.fasterxml.jackson.databind.JsonNode;

public interface ExportedEvent {
    String aggregateId();

    String aggregateType();

    JsonNode payload();

    String type();
}
