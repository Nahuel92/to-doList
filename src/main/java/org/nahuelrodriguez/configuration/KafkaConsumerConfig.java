package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
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
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TodoItemRequestDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TodoItemRequestDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<TodoItemRequest, TodoItemRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new TodoItemRequestDeserializer(),
                new TodoItemRequestDeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<TodoItemRequest, TodoItemRequest> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<TodoItemRequest, TodoItemRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
