package org.nahuelrodriguez.service.implementation;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.service.MessagingQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KafkaProducer implements MessagingQueueProducer {
    private final KafkaTemplate<String, NewTodoItemRequest> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaProducer(final KafkaTemplate<String, NewTodoItemRequest> kafkaTemplate,
                         @Value("${spring.kafka.template.default-topic}") final String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(final Collection<NewTodoItemRequest> dtos) {
        dtos.forEach(dto -> this.kafkaTemplate.send(topic, dto));
    }
}
