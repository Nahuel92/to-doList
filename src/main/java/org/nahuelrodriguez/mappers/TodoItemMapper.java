package org.nahuelrodriguez.mappers;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;

public class TodoItemMapper {
    public TodoItem from(final TodoItemDTO dto) {
        final TodoItem item = new TodoItem();

        item.setId(dto.getId());
        item.setDescription(dto.getDescription());

        return item;
    }
}
