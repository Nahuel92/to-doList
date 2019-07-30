package org.nahuelrodriguez.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;

import java.util.Map;

public class TodoItemRequestSerializer implements Serializer<TodoItemRequest> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(final String s, final TodoItemRequest dto) {
        byte[] retVal;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(dto).getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize DTO:" + dto);
        }
        return retVal;
    }

    @Override
    public void close() {
    }
}
