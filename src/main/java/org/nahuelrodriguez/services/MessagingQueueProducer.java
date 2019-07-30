package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.TodoItemRequest;

import java.util.Collection;

public interface MessagingQueueProducer {
    void sendMessage(final Collection<TodoItemRequest> dtos);
}
