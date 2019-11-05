package org.nahuelrodriguez.mappers;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;

public class TodoItemMapper {
    public TodoItem from(final NewTodoItemRequest dto) {
        final var entity = new TodoItem();
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
