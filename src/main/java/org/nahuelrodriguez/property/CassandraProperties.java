package org.nahuelrodriguez.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.cassandra")
public class CassandraProperties {
    private String contactPoints;
    private String keyspaceName;
    private Integer port;

    public String getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(String contactPoints) {
        this.contactPoints = contactPoints;
    }

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
