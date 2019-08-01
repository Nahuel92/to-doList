package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
import org.nahuelrodriguez.services.MessagingQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KafkaProducer implements MessagingQueueProducer {
    private final KafkaTemplate<String, TodoItemRequest> kafkaTemplate;

    @Autowired
    public KafkaProducer(final KafkaTemplate<String, TodoItemRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(final Collection<TodoItemRequest> dtos) {
        dtos.forEach(dto -> this.kafkaTemplate.send("${spring.kafka.template.default-topic}", dto));
    }
}
