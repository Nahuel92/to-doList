package org.nahuelrodriguez.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private Template template;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getDefaultTopic() {
        return template.getDefaultTopic();
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    private static class Template {
        private String defaultTopic;

        public String getDefaultTopic() {
            return defaultTopic;
        }

        public void setDefaultTopic(String defaultTopic) {
            this.defaultTopic = defaultTopic;
        }
    }
}
