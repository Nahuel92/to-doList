package org.nahuelrodriguez.configuration;

import org.nahuelrodriguez.property.CassandraProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
    private final CassandraProperties properties;
    private static final int defaultPort = 9042;

    public CassandraConfig(final CassandraProperties properties) {
        this.properties = properties;
    }

    @Override
    protected String getKeyspaceName() {
        return properties.getKeyspaceName();
    }

    @Override
    protected String getContactPoints() {
        return properties.getContactPoints();
    }

    @Override
    protected int getPort() {
        return properties.getPort() != null ? properties.getPort() : defaultPort;
    }

    @Override
    public List<String> getStartupScripts() {
        final var createKeyspace = "CREATE KEYSPACE IF NOT EXISTS " + properties.getKeyspaceName() + " WITH durable_writes = true" +
                " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";

        final var createTable = "CREATE TABLE IF NOT EXISTS todolist.to_do_items " +
                "(id UUID PRIMARY KEY, description TEXT, status TEXT, createddatetime TIMESTAMP) ";
        return List.of(createKeyspace, createTable);
    }
}
