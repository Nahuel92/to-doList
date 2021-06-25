package org.nahuelrodriguez.service.implementation;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final TodoListService service;

    @Autowired
    public KafkaConsumer(final TodoListService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final NewTodoItemRequest dto) {
        service.addNewTodoItem(dto);
    }
}
