package org.nahuelrodriguez.mapper;

import org.nahuelrodriguez.entity.TodoItem;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;

public class TodoItemMapper {
    public TodoItem from(final NewTodoItemRequest dto) {
        final var entity = new TodoItem();
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
