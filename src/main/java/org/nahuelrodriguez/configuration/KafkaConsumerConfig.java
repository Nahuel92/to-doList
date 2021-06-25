package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.nahuelrodriguez.property.KafkaProperties;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.serializer.TodoItemRequestDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    private final KafkaProperties properties;

    public KafkaConsumerConfig(final KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        final var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TodoItemRequestDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TodoItemRequestDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<NewTodoItemRequest, NewTodoItemRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new TodoItemRequestDeserializer(),
                new TodoItemRequestDeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<NewTodoItemRequest, NewTodoItemRequest> kafkaListenerContainerFactory() {
        final var factory = new ConcurrentKafkaListenerContainerFactory<NewTodoItemRequest, NewTodoItemRequest>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
