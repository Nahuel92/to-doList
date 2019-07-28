package org.nahuelrodriguez.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic todoListUsersTopic() {
        return new NewTopic("${spring.kafka.template.default-topic}", 1, (short) 1);
    }
}
