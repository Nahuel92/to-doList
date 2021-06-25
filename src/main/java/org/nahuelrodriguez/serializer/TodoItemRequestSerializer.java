package org.nahuelrodriguez.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;

public class TodoItemRequestSerializer implements Serializer<NewTodoItemRequest> {
    @Override
    public byte[] serialize(final String s, final NewTodoItemRequest request) {
        try {
            return new ObjectMapper().writeValueAsString(request).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize DTO:" + request);
        }
    }
}
