package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.serializers.TodoItemDTODeserializer;
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
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TodoItemDTODeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TodoItemDTODeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, TodoItemDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory(
                consumerConfigs(),
                new TodoItemDTODeserializer(),
                new TodoItemDTODeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TodoItemDTO> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, TodoItemDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
