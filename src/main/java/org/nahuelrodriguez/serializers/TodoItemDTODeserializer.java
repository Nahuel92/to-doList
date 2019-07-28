package org.nahuelrodriguez.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.nahuelrodriguez.dtos.TodoItemDTO;

import java.util.Map;

public class TodoItemDTODeserializer implements Deserializer<TodoItemDTO> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public TodoItemDTO deserialize(final String topic, final byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        TodoItemDTO dto;
        try {
            dto = mapper.readValue(data, TodoItemDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize data of topic: " + topic);
        }
        return dto;
    }

    @Override
    public void close() {

    }
}
