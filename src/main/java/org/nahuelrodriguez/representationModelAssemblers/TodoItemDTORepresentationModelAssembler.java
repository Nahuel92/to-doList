package org.nahuelrodriguez.representationModelAssemblers;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.mappers.TodoItemDTOMapper;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class TodoItemDTORepresentationModelAssembler implements RepresentationModelAssembler<TodoItem, TodoItemDTO> {
    private final TodoItemDTOMapper mapper;

    public TodoItemDTORepresentationModelAssembler() {
        this.mapper = new TodoItemDTOMapper();
    }

    @Override
    public TodoItemDTO toModel(final TodoItem entity) {
        return mapper.from(entity);
    }
}
