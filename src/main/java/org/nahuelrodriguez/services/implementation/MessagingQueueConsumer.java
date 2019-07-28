package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.dtos.TodoItemDTO;

import java.util.Collection;

public interface MessagingQueueConsumer {
    void consume(final Collection<TodoItemDTO> dtos);
}
