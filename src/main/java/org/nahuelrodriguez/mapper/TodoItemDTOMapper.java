package org.nahuelrodriguez.mapper;

import org.nahuelrodriguez.entity.TodoItem;
import org.nahuelrodriguez.response.dto.TodoItemDTO;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TodoItemDTOMapper {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy MM dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public Mono<TodoItemDTO> from(final Mono<TodoItem> monoEntity) {
        return monoEntity.map(this::from);
    }

    public TodoItemDTO from(final TodoItem entity) {
        final var dto = new TodoItemDTO();
        dto.setId(entity.getId().toString());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDatetime(DATE_TIME_FORMATTER.format(entity.getCreatedDatetime()));
        return dto;
    }
}
