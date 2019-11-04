package org.nahuelrodriguez.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;

import java.io.IOException;
import java.util.Map;

public class TodoItemRequestDeserializer implements Deserializer<NewTodoItemRequest> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public NewTodoItemRequest deserialize(final String topic, final byte[] data) {
        final NewTodoItemRequest dto;
        try {
            dto = new ObjectMapper().readValue(data, NewTodoItemRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize data of topic: " + topic);
        }
        return dto;
    }

    @Override
    public void close() {
    }
}
