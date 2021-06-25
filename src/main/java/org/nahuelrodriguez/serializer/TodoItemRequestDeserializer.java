package org.nahuelrodriguez.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;

import java.io.IOException;

public class TodoItemRequestDeserializer implements Deserializer<NewTodoItemRequest> {
    @Override
    public NewTodoItemRequest deserialize(final String topic, final byte[] data) {
        try {
            return new ObjectMapper().readValue(data, NewTodoItemRequest.class);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to deserialize data of topic: " + topic);
        }
    }
}
