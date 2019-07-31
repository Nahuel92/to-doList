package org.nahuelrodriguez.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${cassandra.contactPoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keySpace;

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    protected List<String> getStartupScripts() {
        final String createKeyspace =
                "CREATE KEYSPACE IF NOT EXISTS " + keySpace + " WITH durable_writes = true" +
                        " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";

        final String createTable = "CREATE TABLE IF NOT EXISTS todolist.to_do_items " +
                "(id int primary key, createddatetime timestamp, description text) ";
        return List.of(createKeyspace, createTable);
    }
}
