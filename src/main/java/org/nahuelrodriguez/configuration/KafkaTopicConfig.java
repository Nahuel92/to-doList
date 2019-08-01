package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
    private final String topic;

    public KafkaTopicConfig(@Value("${spring.kafka.template.default-topic}") final String topic) {
        this.topic = topic;
    }

    @Bean
    public NewTopic todoListUsersTopic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
