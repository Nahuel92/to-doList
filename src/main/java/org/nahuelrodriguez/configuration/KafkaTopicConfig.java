package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.nahuelrodriguez.property.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
    private final KafkaProperties properties;

    public KafkaTopicConfig(final KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NewTopic todoListUsersTopic() {
        return new NewTopic(properties.getDefaultTopic(), 1, (short) 1);
    }
}
