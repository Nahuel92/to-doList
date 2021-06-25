package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.nahuelrodriguez.property.KafkaProperties;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.serializer.TodoItemRequestSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    private final KafkaProperties properties;

    public KafkaProducerConfig(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        final var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, TodoItemRequestSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TodoItemRequestSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, NewTodoItemRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, NewTodoItemRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
