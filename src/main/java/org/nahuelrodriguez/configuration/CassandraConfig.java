package org.nahuelrodriguez.configuration;

import org.nahuelrodriguez.properties.CassandraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractClusterConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractClusterConfiguration {
    private final CassandraProperties properties;

    public CassandraConfig(final CassandraProperties properties) {
        this.properties = properties;
    }

    @Override
    protected List<String> getStartupScripts() {
        final var createKeyspace = "CREATE KEYSPACE IF NOT EXISTS " + properties.getKeyspaceName() + " WITH durable_writes = true" +
                " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";

        final var createTable = "CREATE TABLE IF NOT EXISTS todolist.to_do_items " +
                "(id UUID PRIMARY KEY, description TEXT, status TEXT, createddatetime TIMESTAMP) ";
        return List.of(createKeyspace, createTable);
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        final var bean = new CassandraClusterFactoryBean();
        bean.setMetricsEnabled(false);
        bean.setStartupScripts(getStartupScripts());
        return bean;
    }
}
