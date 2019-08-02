package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
import org.nahuelrodriguez.services.MessagingQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KafkaProducer implements MessagingQueueProducer {
    private final KafkaTemplate<String, TodoItemRequest> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaProducer(final KafkaTemplate<String, TodoItemRequest> kafkaTemplate,
                         @Value("${spring.kafka.template.default-topic}") final String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(final Collection<TodoItemRequest> dtos) {
        dtos.forEach(dto -> this.kafkaTemplate.send(topic, dto));
    }
}
