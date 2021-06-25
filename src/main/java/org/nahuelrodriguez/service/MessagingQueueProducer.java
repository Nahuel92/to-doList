package org.nahuelrodriguez.service;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;

import java.util.Collection;

public interface MessagingQueueProducer {
    void sendMessage(final Collection<NewTodoItemRequest> dtos);
}
