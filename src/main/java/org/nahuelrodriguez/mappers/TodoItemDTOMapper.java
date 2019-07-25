package org.nahuelrodriguez.mappers;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TodoItemDTOMapper {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy mm dd HH:MM:SS")
            .withZone(ZoneId.systemDefault());

    public TodoItemDTO from(TodoItem entity) {
        TodoItemDTO dto = new TodoItemDTO();

        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setCreatedDatetime(dtf.format(entity.getCreatedDatetime()));

        return dto;
    }
}
