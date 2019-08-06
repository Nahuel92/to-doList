package org.nahuelrodriguez.mappers;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;

public class TodoItemMapper {
    public TodoItem from(final TodoItemRequest dto) {
        final var entity = new TodoItem();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());

        return entity;
    }
}
