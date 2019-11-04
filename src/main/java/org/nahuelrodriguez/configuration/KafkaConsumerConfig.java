package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.serializers.TodoItemRequestDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    private final String bootstrapServers;

    public KafkaConsumerConfig(@Value("${spring.kafka.bootstrap-servers}") final String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        final var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
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
