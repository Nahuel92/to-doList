package org.nahuelrodriguez.mappers;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;

public class TodoItemMapper {
    public TodoItem from(final TodoItemRequest dto) {
        final TodoItem item = new TodoItem();

        item.setId(dto.getId());
        item.setDescription(dto.getDescription());

        return item;
    }
}
