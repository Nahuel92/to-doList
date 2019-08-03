package org.nahuelrodriguez.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;

import java.util.Map;

public class TodoItemRequestDeserializer implements Deserializer<TodoItemRequest> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TodoItemRequest deserialize(final String topic, final byte[] data) {
        final var mapper = new ObjectMapper();
        TodoItemRequest dto;
        try {
            dto = mapper.readValue(data, TodoItemRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize data of topic: " + topic);
        }
        return dto;
    }

    @Override
    public void close() {
    }
}
