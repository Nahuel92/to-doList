package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.MessagingQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KafkaProducer implements MessagingQueueProducer {
    @Value("${spring.kafka.template.default-topic}")
    private String topic;
    private KafkaTemplate<String, TodoItemDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(final KafkaTemplate<String, TodoItemDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(final Collection<TodoItemDTO> dtos) {
        dtos.forEach(dto -> this.kafkaTemplate.send(topic, dto));
    }
}
