package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;

import java.util.Collection;

public interface MessagingQueueProducer {
    void sendMessage(final Collection<TodoItemDTO> dtos);
}
