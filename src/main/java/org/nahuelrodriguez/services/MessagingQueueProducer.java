package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;

import java.util.Collection;

public interface MessagingQueueProducer {
    void sendMessage(final Collection<NewTodoItemRequest> dtos);
}
