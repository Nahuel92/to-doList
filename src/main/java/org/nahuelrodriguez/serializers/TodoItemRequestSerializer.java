package org.nahuelrodriguez.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;

import java.util.Map;

public class TodoItemRequestSerializer implements Serializer<NewTodoItemRequest> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(final String s, final NewTodoItemRequest request) {
        final byte[] serializedDTO;
        try {
            serializedDTO = new ObjectMapper().writeValueAsString(request).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize DTO:" + request);
        }
        return serializedDTO;
    }

    @Override
    public void close() {
    }
}
